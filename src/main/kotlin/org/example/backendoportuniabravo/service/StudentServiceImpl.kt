package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.mapper.StudentMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository,
    private val profileRepository: ProfileRepository,
    private val languageRepository: LanguageRepository,
    private val degreeRepository: DegreeRepository,
    private val interestRepository: InterestRepository,
    private val internshipRepository: InternshipRepository,
    private val collegeRepository: CollegeRepository,
    @Autowired
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val studentMapper: StudentMapper,

    ) : StudentService {

    @Transactional
    override fun create(dto: StudentCreateRequestDTO): StudentResponseDTO {
        val userInput = dto.user

        if (userRepository.existsByEmail(userInput.email!!)) {
            throw IllegalArgumentException("User with email '${userInput.email}' already exists")
        }

        userInput.password ?:
        throw IllegalArgumentException("User password cannot be null")

        val role = roleRepository.findByName("ROLE_STUDENT")
            .orElseThrow { NoSuchElementException("Role not found") }



        val user = User(
            firstName = userInput.firstName ?: throw IllegalArgumentException("User name cannot be null"),
            lastName = userInput.lastName ?: throw IllegalArgumentException("User last name cannot be null"),
            email = userInput.email ?: throw IllegalArgumentException("User email cannot be null"),
            password = passwordEncoder.encode(userInput.password),
            createDate = Date(),
            tokenExpired = false,
            enabled = true,
            roleList = mutableSetOf(role),
        )

        val profile = Profile(user = user, verified = false)
        user.profile = profile

        userRepository.save(user) // guarda en cascada también el profile si está mapeado con cascade
        profileRepository.save(profile)

        val student = Student(
            profile = profile,
            description = dto.description,
            academicCenter = dto.academicCenter
        )

        dto.hobbies.forEach { name ->
            student.hobbies?.add(Hobby(name = name, student = student))
        }

        dto.certifications.forEach {
            student.certifications?.add(
                Certification(
                    name = it.name,
                    date = it.date,
                    organization = it.organization,
                    student = student
                )
            )
        }

        dto.experiences.forEach {
            student.experiences?.add(
                Experience(
                    name = it.name,
                    description = it.description,
                    student = student
                )
            )
        }

        dto.skills.forEach {
            student.skills?.add(Skill(name = it.name, description = it.description, student = student))
        }

        dto.careers.forEach {
            student.careers?.add(Career(career = it, student = student))
        }

        dto.cvUrls.forEach {
            student.cvUrls?.add(CVUrl(url = it, student = student))
        }

        student.languages = languageRepository.findAllById(dto.languagesIds).toMutableSet()
        student.degrees = degreeRepository.findAllById(dto.degreesIds).toMutableSet()
        student.colleges = collegeRepository.findAllById(dto.collegesIds).toMutableSet()
        student.interests = interestRepository.findAllById(dto.interestsIds).toMutableSet()
        student.internships = internshipRepository.findAllById(dto.internshipsIds).toMutableSet()

        return studentMapper.toStudentResponseDto(studentRepository.save(student))
    }



    override fun findById(id: Long): StudentResponseDTO {
        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }

        return studentMapper.toStudentResponseDto(student)
    }

    override fun findByUsername(username: String): StudentProfileResponseDTO {
        val user = userRepository.findByEmail(username)
            ?: throw RuntimeException("User not found")

        val student = studentRepository.findByProfileId(user.profile?.id ?: throw RuntimeException("Profile not found"))
            ?: throw RuntimeException("Student not found for user $username")

        return studentMapper.toStudentProfileDto(student)

    }

    override fun findAll(): List<StudentResponseDTO> {
        return studentRepository.findAll().map(studentMapper::toStudentResponseDto)
    }

    override fun delete(id: Long) {
        if (!studentRepository.existsById(id)) {
            throw RuntimeException("Student not found")
        }
        studentRepository.deleteById(id)
    }

    override fun update(id: Long, dto: StudentRequestDTO): StudentResponseDTO {
        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }

        studentMapper.updateFromDto(dto, student)
        return studentMapper.toStudentResponseDto(studentRepository.save(student))
    }

    override fun returnStudentCurriculum(id: Long): StudentCurriculumResponseDTO {
        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }

        return studentMapper.toCurriculumDto(student)
    }

    override fun registerStudent(dto: StudentRegister): StudentResponseDTO {

        // 1. Validaciones básicas
        if (userRepository.existsByEmail(dto.email)) {
            throw IllegalArgumentException("User with email '${dto.email}' already exists")
        }

        if (dto.password != dto.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        // 2. Obtener rol y entidades referenciadas
        val role = roleRepository.findByName("ROLE_STUDENT")
            .orElseThrow { NoSuchElementException("Role not found") }

        val college = collegeRepository.findById(dto.college.id!!)
            .orElseThrow { NoSuchElementException("College not found with id ${dto.college.id}") }

        val degree = degreeRepository.findById(dto.degree.id!!)
            .orElseThrow { NoSuchElementException("Degree not found with id ${dto.degree.id}") }

        val interests: Set<Interest> = dto.interest.map { interestDto ->
            interestRepository.findById(interestDto.id!!)
                .orElseThrow { NoSuchElementException("Interest not found with id ${interestDto.id}") }
        }.toSet()

        // 3. Crear User y Profile
        val user = User(
            firstName = dto.firstName,
            lastName = dto.lastName,
            email = dto.email,
            password = passwordEncoder.encode(dto.password),
            createDate = Date(),
            tokenExpired = false,
            enabled = true,
            roleList = mutableSetOf(role)
        )

        val profile = Profile(user = user, verified = false)
        user.profile = profile

        // 4. Crear Student
        val student = Student(
            profile = profile,
            colleges = mutableSetOf(college),
            degrees = mutableSetOf(degree),
            interests = interests.toMutableSet(),
            academicCenter = college.name
        )
        profile.student = student

        // 5. Guardar
        userRepository.save(user) // cascade guarda profile y student
        profileRepository.save(profile)
        val savedStudent = studentRepository.save(student)


        // 6. Retornar DTO de respuesta (puedes adaptar según tu modelo)
        return studentMapper.mapStudentToStudentResponseDTO(savedStudent)
    }

}

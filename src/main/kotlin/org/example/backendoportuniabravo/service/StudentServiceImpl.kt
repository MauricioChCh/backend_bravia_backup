package org.example.backendoportuniabravo.service

import jakarta.persistence.EntityNotFoundException
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

        userInput.password ?: throw IllegalArgumentException("User password cannot be null")

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
                org.example.backendoportuniabravo.entity.Experience(
                    name = it.name,
                    description = it.description,
                    student = student
                )
            )
        }

        dto.skills.forEach {
            student.skills?.add(
                org.example.backendoportuniabravo.entity.Skill(
                    name = it.name,
                    description = it.description,
                    student = student
                )
            )
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

    override fun returnStudentCurriculum(studentId: Long): StudentCurriculumResponseDTO {
        val student = studentRepository.findById(studentId)
            .orElseThrow { EntityNotFoundException("Student not found") }

        return StudentCurriculumResponseDTO(
            id = student.id ?: 0L,
            userInput = UserResult(
                firstName = student.profile?.user?.firstName ?: "",
                lastName = student.profile?.user?.lastName ?: "",
                email = student.profile?.user?.email ?: ""
            ),
            description = student.description ?: "",
            academicCenter = student.academicCenter ?: "",
            hobbies = student.hobbies?.map { HobbyDTO(id = it.id ?: 0L, name = it.name) }?.toMutableList() ?: mutableListOf(),
             certifications = student.certifications?.map {
                CertificationDTO(
                    name = it.name,
                    date = it.date,
                    organization = it.organization
                )
            }?.toMutableList() ?: mutableListOf(),
            experiences = student.experiences?.map {
                ExperienceDTO(
                    name = it.name,
                    description = it.description
                )
            }?.toMutableList() ?: mutableListOf(),
            skills = student.skills?.map {
                SkillDTO(
                    name = it.name,
                    description = it.description
                )
            }?.toMutableList() ?: mutableListOf(),
            careers = student.careers?.map { CareerDTO(id = it.id ?: 0L, career = it.career) }?.toMutableList() ?: mutableListOf()
        )
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

// NOTA: Estas data classes deberían ir en un archivo separado o ser renombradas
// para evitar conflictos con las entidades JPA

// Alternativa 1: Usar nombres diferentes
data class EducationData(val degree: String, val institution: String, val year: Int) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "degree" to degree,
            "institution" to institution,
            "year" to year
        )
    }
}

data class ExperienceData(val name: String, val description: String) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "description" to description
        )
    }
}

data class SkillData(val name: String, val description: String) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "description" to description
        )
    }
}

// Alternativa 2: Mover estas classes a un paquete diferente
// package org.example.backendoportuniabravo.dto.curriculum

// Alternativa 3: Usar extension functions en las entidades JPA
// fun org.example.backendoportuniabravo.entity.Experience.toMap(): Map<String, Any> {
//     return mapOf(
//         "name" to (this.name ?: ""),
//         "description" to (this.description ?: "")
//     )
// }
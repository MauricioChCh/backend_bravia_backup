package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.StudentCreateRequestDTO
import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.mapper.StudentMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository,
    private val profileRepository: ProfileRepository,
    private val mapper: StudentMapper,
    private val languageRepository: LanguageRepository,
    private val degreeRepository: DegreeRepository,
    private val interestRepository: InterestRepository,
    private val internshipRepository: InternshipRepository,
    private val collegeRepository: CollegeRepository,
    @Autowired
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder,

    ) : StudentService {

    @Transactional
    override fun create(dto: StudentCreateRequestDTO): StudentResponseDTO {
        val userInput = dto.user ?: throw IllegalArgumentException("User input is required")

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
            student.hobbies.add(Hobby(name = name, student = student))
        }

        dto.certifications.forEach {
            student.certifications.add(
                Certification(
                    name = it.name,
                    date = it.date,
                    organization = it.organization,
                    student = student
                )
            )
        }

        dto.experiences.forEach {
            student.experiences.add(
                Experience(
                    name = it.name,
                    description = it.description,
                    student = student
                )
            )
        }

        dto.skills.forEach {
            student.skills.add(Skill(name = it.name, description = it.description, student = student))
        }

        dto.careers.forEach {
            student.careers.add(Career(career = it, student = student))
        }

        dto.cvUrls.forEach {
            student.cvUrls.add(CVUrl(url = it, student = student))
        }

        student.languages = languageRepository.findAllById(dto.languagesIds).toMutableSet()
        student.degrees = degreeRepository.findAllById(dto.degreesIds).toMutableSet()
        student.colleges = collegeRepository.findAllById(dto.collegesIds).toMutableSet()
        student.interests = interestRepository.findAllById(dto.interestsIds).toMutableSet()
        student.internships = internshipRepository.findAllById(dto.internshipsIds).toMutableSet()

        return mapper.toDto(studentRepository.save(student))
    }



    override fun findById(id: Long): StudentResponseDTO {
        val student = studentRepository.findById(id)
            .orElseThrow { RuntimeException("Student not found") }

        return mapper.toDto(student)
    }

    override fun findAll(): List<StudentResponseDTO> {
        return studentRepository.findAll().map(mapper::toDto)
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

        mapper.updateFromDto(dto, student)
        return mapper.toDto(studentRepository.save(student))
    }

}

package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.mapper.StudentMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
) : StudentService {

    @Transactional
    override  fun create(dto: StudentRequestDTO): StudentResponseDTO {
        val profile = profileRepository.findById(dto.profileId)
            .orElseThrow { ChangeSetPersister.NotFoundException() }

        val student = Student(
            profile = profile,
            description = dto.description,
            academicCenter = dto.academicCenter
        )

        dto.hobbies.forEach { name -> student.hobbies.add(Hobbie(name = name, student = student)) }

        dto.certifications.forEach {
            student.certifications.add(
                Certification(name = it.name, date = it.date, organization = it.organization, student = student)
            )
        }

        dto.experiences.forEach {
            student.experiences.add(
                Experience(name = it.name, description = it.description, student = student)
            )
        }

        dto.skills.forEach {
            student.skills.add(Skill(name = it.name, description = it.description, student = student))
        }

        dto.careers.forEach {
            student.careers.add(Career(carrer = it, student = student))
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

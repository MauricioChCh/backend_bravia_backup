package org.example.backendoportuniabravo.mapper.impl

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entities.*
import org.example.backendoportuniabravo.mapper.StudentMapper
import org.example.backendoportuniabravo.repositories.*
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StudentMapperImpl : StudentMapper {

    @Autowired
    private lateinit var languageRepository: LanguageRepository

    @Autowired
    private lateinit var degreeRepository: DegreeRepository

    @Autowired
    private lateinit var collegeRepository: CollegeRepository

    @Autowired
    private lateinit var interestRepository: InterestRepository

    @Autowired
    private lateinit var internshipRepository: InternshipRepository

    override fun toDto(student: Student): StudentResponseDTO {
        // Implementa la conversión de Student a StudentResponseDTO
        return StudentResponseDTO(
            id = student.id,
            description = student.description,
            academicCenter = student.academicCenter,
            // Mapea otros campos según sea necesario
        )
    }

    override fun toDtoList(list: List<Student>): List<StudentResponseDTO> {
        return list.map { toDto(it) }
    }

    override fun toEntity(dto: StudentRequestDTO, profile: Profile): Student {
        val student = Student(
            profile = profile,
            description = dto.description,
            academicCenter = dto.academicCenter,
            hobbies = mutableListOf(),
            certifications = mutableListOf(),
            experiences = mutableListOf(),
            skills = mutableListOf(),
            careers = mutableListOf(),
            cvUrls = mutableListOf(),
            languages = mutableSetOf(),
            degrees = mutableSetOf(),
            colleges = mutableSetOf(),
            interests = mutableSetOf(),
            internships = mutableSetOf()
        )

        // Mapear hobbies
        dto.hobbies.forEach { name ->
            student.hobbies.add(Hobbie(name = name, student = student))
        }

        // Mapear certificaciones
        dto.certifications.forEach { certDto ->
            student.certifications.add(
                Certification(
                    name = certDto.name,
                    date = certDto.date,
                    organization = certDto.organization,
                    student = student
                )
            )
        }

        // Mapear experiencias
        dto.experiences.forEach { expDto ->
            student.experiences.add(
                Experience(
                    name = expDto.name,
                    description = expDto.description,
                    student = student
                )
            )
        }

        // Mapear habilidades
        dto.skills.forEach { skillDto ->
            student.skills.add(
                Skill(
                    name = skillDto.name,
                    description = skillDto.description,
                    student = student
                )
            )
        }

        // Mapear carreras
        dto.careers.forEach { carrer ->
            student.careers.add(Career(carrer = carrer, student = student))
        }

        // Mapear URLs de CV
        dto.cvUrls.forEach { url ->
            student.cvUrls.add(CVUrl(url = url, student = student))
        }

        // Mapear relaciones many-to-many
        student.languages = languageRepository.findAllById(dto.languagesIds).toMutableSet()
        student.degrees = degreeRepository.findAllById(dto.degreesIds).toMutableSet()
        student.colleges = collegeRepository.findAllById(dto.collegesIds).toMutableSet()
        student.interests = interestRepository.findAllById(dto.interestsIds).toMutableSet()
        student.internships = internshipRepository.findAllById(dto.internshipsIds).toMutableSet()

        return student
    }

    override fun updateFromDto(dto: StudentRequestDTO, student: Student) {
        // Implementar la actualización de Student desde StudentRequestDTO
        student.description = dto.description
        student.academicCenter = dto.academicCenter

        // Actualizar otras propiedades según sea necesario
    }
}
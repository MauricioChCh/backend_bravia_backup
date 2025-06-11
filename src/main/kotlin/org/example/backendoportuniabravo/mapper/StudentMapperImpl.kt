package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*
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
        val user = student.profile?.user

        val userResult = UserResult(
            id = user?.id,
            email = user?.email,
            firstName = user?.firstName,
            lastName = user?.lastName
        )

        return StudentResponseDTO(
            id = student.id,
            description = student.description!!,
            academicCenter = student.academicCenter!!,
            userInput = userResult
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
            student.hobbies?.add(Hobby(name = name, student = student))
        }

        // Mapear certificaciones
        dto.certifications.forEach { certDto ->
            student.certifications?.add(
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
            student.experiences?.add(
                Experience(
                    name = expDto.name,
                    description = expDto.description,
                    student = student
                )
            )
        }

        // Mapear habilidades
        dto.skills.forEach { skillDto ->
            student.skills?.add(
                Skill(
                    name = skillDto.name,
                    description = skillDto.description,
                    student = student
                )
            )
        }

        // Mapear carreras
        dto.careers.forEach { career ->
            student.careers?.add(Career(career = career, student = student))
        }

        // Mapear URLs de CV
        dto.cvUrls.forEach { url ->
            student.cvUrls?.add(CVUrl(url = url, student = student))
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

    override fun toCurriculumDto(student: Student): StudentCurriculumResponseDTO {
        val user = student.profile?.user

        val userResult = UserResult(
            id = user?.id,
            email = user?.email,
            firstName = user?.firstName,
            lastName = user?.lastName
        )

        return StudentCurriculumResponseDTO(
            id = student.id ?: 0L,
            userInput = userResult,
            description = student.description!!,
            academicCenter = student.academicCenter!!,
            hobbies = mapHobbies(student.hobbies!!).toMutableList(),
            certifications = mapCertifications(student.certifications!!).toMutableList(),
            experiences = mapExperiences(student.experiences!!).toMutableList(),
            skills = mapSkills(student.skills!!).toMutableList(),
            careers = mapCareers(student.careers!!).toMutableList()
        )
    }

    override fun mapHobbies(hobbies: List<Hobby>): List<HobbyDTO> {
        return hobbies.map { HobbyDTO(it.id, it.name) }
    }

    override fun mapCertifications(certifications: List<Certification>): List<CertificationDTO> {
        return certifications.map {
            CertificationDTO( it.name, it.date, it.organization)
        }
    }

    override fun mapExperiences(experiences: List<Experience>): List<ExperienceDTO> {
        return experiences.map {
            ExperienceDTO(it.name, it.description)
        }
    }

    override fun mapSkills(skills: List<Skill>): List<SkillDTO> {
        return skills.map {
            SkillDTO(it.name, it.description)
        }
    }

    override fun mapCareers(careers: List<Career>): List<CareerDTO> {
        return careers.map {
            CareerDTO(it.id, it.career)
        }
    }

    override fun mapStudentToStudentResponseDTO(student: Student): StudentResponseDTO {
        val user = student.profile?.user

        val userResult = UserResult(
            id = user?.id,
            email = user?.email,
            firstName = user?.firstName,
            lastName = user?.lastName
        )

        return StudentResponseDTO(
            id = student.id,
            description = student.description!!,
            academicCenter = student.academicCenter!!,
            userInput = userResult
        )
    }



}
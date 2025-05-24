package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.CareerDTO
import org.example.backendoportuniabravo.dto.CertificationDTO
import org.example.backendoportuniabravo.dto.ExperienceDTO
import org.example.backendoportuniabravo.dto.HobbyDTO
import org.example.backendoportuniabravo.dto.SkillDTO
import org.example.backendoportuniabravo.dto.StudentCurriculumResponseDTO
import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.entity.Career
import org.example.backendoportuniabravo.entity.Certification
import org.example.backendoportuniabravo.entity.Experience
import org.example.backendoportuniabravo.entity.Hobby
import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.Skill
import org.example.backendoportuniabravo.entity.Student
import org.mapstruct.*

interface StudentMapper {
    fun toDto(student: Student): StudentResponseDTO

    fun toDtoList(list: List<Student>): List<StudentResponseDTO>

    fun toEntity(dto: StudentRequestDTO, profile: Profile): Student

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateFromDto(dto: StudentRequestDTO, @MappingTarget student: Student)

    fun toCurriculumDto(student: Student): StudentCurriculumResponseDTO

    fun mapHobbies(hobbies: List<Hobby>): List<HobbyDTO>
    fun mapCertifications(certifications: List<Certification>): List<CertificationDTO>
    fun mapExperiences(experiences: List<Experience>): List<ExperienceDTO>
    fun mapSkills(skills: List<Skill>): List<SkillDTO>
    fun mapCareers(careers: List<Career>): List<CareerDTO>
}
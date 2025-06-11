package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.*
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

    fun mapStudentToStudentResponseDTO(student: Student): StudentResponseDTO

//    fun mapCollegeDetailsToCollege(collegeDetails: CollegeDetails): College
//    fun mapDegreeDetailsToDegree(degreeDetails: DegreeDetails): Degree
//    fun mapInterestDetailsToInterest(interestDetails: InterestDetails): Interest
}


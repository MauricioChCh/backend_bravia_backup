package org.example.backendoportuniabravo.dto

data class StudentResponseDTO(
    val id: Long?,
//    val fullName: String,
    val description: String,
    val academicCenter: String,
    val userInput: UserResult
)

data class StudentCurriculumResponseDTO(
    val id: Long,
    val userInput: UserResult,
    val description: String,
    val academicCenter: String,
    val hobbies: MutableList<HobbyDTO>,
    val certifications: MutableList<CertificationDTO>,
    val experiences: MutableList<ExperienceDTO>,
    val skills: MutableList<SkillDTO>,
    val careers: MutableList<CareerDTO>
)




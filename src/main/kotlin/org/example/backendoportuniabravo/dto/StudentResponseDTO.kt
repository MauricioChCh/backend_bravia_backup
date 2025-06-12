package org.example.backendoportuniabravo.dto
import org.example.backendoportuniabravo.dto.InstitutionDTO
import org.example.backendoportuniabravo.dto.LocationDTO
import org.example.backendoportuniabravo.dto.CertificateDTO
import org.example.backendoportuniabravo.dto.LanguageDTO
import org.example.backendoportuniabravo.dto.DegreeDTO
import org.example.backendoportuniabravo.dto.CollegeDTO
import org.example.backendoportuniabravo.dto.InterestDTO
import org.example.backendoportuniabravo.dto.EducationDTO
import org.example.backendoportuniabravo.dto.ContactDTO



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

data class StudentProfileResponseDTO(
    val id: Long?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val description: String?,
    val academicCenter: String?,
    val institution: InstitutionDTO? = null,
    val location: LocationDTO? = null,
    val hobbies: List<HobbyDTO>? = null,
    val certifications: List<CertificationDTO>? = null,
    val certificates: List<CertificateDTO>? = null,
    val experiences: List<ExperienceDTO>? = null,
    val skills: List<SkillDTO>? = null,
    val careers: List<CareerDTO>? = null,
    val cvUrls: List<String>? = null,
    val languages: List<LanguageDTO>? = null,
    val degrees: List<DegreeDTO>? = null,
    val colleges: List<CollegeDTO>? = null,
    val interests: List<InterestDTO>? = null,
    val education: List<EducationDTO>? = null,
    val contacts: List<ContactDTO>? = null
)



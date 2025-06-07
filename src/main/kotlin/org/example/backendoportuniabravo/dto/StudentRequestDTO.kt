package org.example.backendoportuniabravo.dto

import org.example.backendoportuniabravo.entity.College
import org.example.backendoportuniabravo.entity.Degree
import org.example.backendoportuniabravo.entity.Interest
import org.example.backendoportuniabravo.entity.Language
import java.util.*
//data class StudentRequestDTO(
//    val profileId: Long,
//    val description: String,
//    val academicCenter: String
//)
data class StudentCreateRequestDTO(
    val user: UserInput,
    val description: String,
    val academicCenter: String,
    val hobbies: List<String> = emptyList(),
    val certifications: List<CertificationDTO> = emptyList(),
    val experiences: List<ExperienceDTO> = emptyList(),
    val skills: List<SkillDTO> = emptyList(),
    val careers: List<String> = emptyList(),
    val cvUrls: List<String> = emptyList(),
    val languagesIds: Set<Long> = emptySet(),
    val degreesIds: Set<Long> = emptySet(),
    val collegesIds: Set<Long> = emptySet(),
    val interestsIds: Set<Long> = emptySet(),
    val internshipsIds: Set<Long> = emptySet()
)



data class StudentRequestDTO(
    val profileId: Long,
    val description: String,
    val academicCenter: String,
    val hobbies: List<String> = emptyList(),
    val certifications: List<CertificationDTO> = emptyList(),
    val experiences: List<ExperienceDTO> = emptyList(),
    val skills: List<SkillDTO> = emptyList(),
    val careers: List<String> = emptyList(),
    val cvUrls: List<String> = emptyList(),
    val languagesIds: Set<Long> = emptySet(),
    val degreesIds: Set<Long> = emptySet(),
    val collegesIds: Set<Long> = emptySet(),
    val interestsIds: Set<Long> = emptySet(),
    val internshipsIds: Set<Long> = emptySet()
)

data class CertificationDTO(
    val name: String,
    val date: Date,
    val organization: String
)

data class ExperienceDTO(
    val name: String,
    val description: String
)

data class SkillDTO(
    val name: String,
    val description: String
)

data class HobbyDTO(val id: Long?, val name: String?)
data class CareerDTO(val id: Long?, val career: String?)

// Data classes para el seeding
data class StudentSeedData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val description: String,
    val academicCenter: String,
    val hobbies: List<String>,
    val certifications: List<CertificationSeedData>,
    val experiences: List<ExperienceSeedData>,
    val skills: List<SkillSeedData>,
    val careers: List<String>,
    val cvUrls: List<String>,
    val languageNames: List<String>,  // Cambiado de Set<Language> a List<String>
    val degreeNames: List<String>,    // Cambiado de Set<Degree> a List<String>
    val collegeNames: List<String>,   // Cambiado de Set<College> a List<String>
    val interestNames: List<String>   // Cambiado de Set<Interest> a List<String>
)

data class CertificationSeedData(
    val name: String,
    val date: String,
    val organization: String
)

data class ExperienceSeedData(
    val name: String,
    val description: String
)

data class SkillSeedData(
    val name: String,
    val description: String
)
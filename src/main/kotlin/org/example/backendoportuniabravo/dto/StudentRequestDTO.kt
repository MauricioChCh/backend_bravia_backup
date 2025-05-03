package org.example.backendoportuniabravo.dto

import java.util.*

//data class StudentRequestDTO(
//    val profileId: Long,
//    val description: String,
//    val academicCenter: String
//)
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



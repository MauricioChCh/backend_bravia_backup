package org.example.backendoportuniabravo.dto

data class EducationDTO(
    val id: Long?,
    val institution: InstitutionDTO?,
    val degree: String?,
    val startDate: String?,
    val endDate: String? = null
)

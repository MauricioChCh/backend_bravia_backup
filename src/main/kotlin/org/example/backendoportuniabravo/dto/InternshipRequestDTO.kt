package org.example.backendoportuniabravo.dto

import java.time.LocalDate
import java.util.*

// Entrada -> cliente backend
data class InternshipRequestDTO(
    val companyId: Long,
    val locationId: Long,
    val title: String,
    val imageUrl: String?,
    val publicationDate: Date,
    val duration: String,
    val salary: Double?,
    val modality: String,
    val schedule: String,
    val requirements: String,
    val activities: String,
    val link: String
)
data class InternshipPatchDTO(
    val title: String? = null,
    val imageUrl: String? = null,
    val publicationDate: Date? = null,
    val duration: String? = null,
    val salary: Double? = null,
    val modality: String? = null,
    val schedule: String? = null,
    val requirements: String? = null,
    val activities: String? = null,
    val link: String? = null,
    val companyId: Long? = null,
    val locationId: Long? = null
)



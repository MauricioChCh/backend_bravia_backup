package org.example.backendoportuniabravo.dto

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

package org.example.backendoportuniabravo.dto

import java.util.*

// Output->  backend -> client
data class InternshipResponseDTO(
    val id: Long?,
    val title: String,
    val companyName: String,
    val cityName: String,
    val countryName: String,
    val modality: String,
    val schedule: String,
    val requirements: String,
    val activities: String,
    val link: String,
    val publicationDate: Date,
    val imageUrl: String? = null,
    val duration: String? = null,
    val salary: Double? = null,

    // Campo calculated
    val locationFullName: String? = "$cityName, $countryName"
)


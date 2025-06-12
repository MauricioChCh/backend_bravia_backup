package org.example.backendoportuniabravo.dto

import java.util.*

/**
 * DTO representing an internship returned to the client.
 * Output: backend to client.
 * Output->  backend -> client
 */
data class InternshipResponseDTO(
    val id: Long?,
    val title: String,
    val description: String? = null,
    val companyName: String,
    val cityName: String? = null,
    val countryName: String,
    val modality: ModalityResponse?,
    val schedule: String,
    val requirements: String,
    val activities: String,
    val link: String,
    val publicationDate: Date,
    val imageUrl: String? = null,
    val duration: String? = null,
    val salary: Double? = null,
    val isBookmarked: Boolean = false,
    /**
     * Computed field combining city and country names.
     */
    val locationFullName: String? = "$cityName, $countryName"
)


data class InternshipResponse(
    val id: Long?,
    val title: String,
    val description: String? = null,
    val companyName: String,
    val cityName: String? = null,
    val countryName: String,
    val modality: String?,
    val schedule: String,
    val requirements: String,
    val activities: String,
    val link: String,
    val publicationDate: Date,
    val imageUrl: String? = null,
    val duration: String? = null,
    val salary: Double? = null,
    val isBookmarked: Boolean = false,
)
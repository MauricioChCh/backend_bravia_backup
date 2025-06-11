package org.example.backendoportuniabravo.dto


import java.util.*

/**
 * DTO for creating or fully updating internships.
 * Input: sent from client to backend.
 * Input -> client backend
 */

data class InternshipRequestDTO(
    val companyId: Long,
    val locationId: Long,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val publicationDate: Date,
    val duration: String,
    val salary: Double?,
    val modality: ModalityRequest,
    val schedule: String,
    val requirements: String,
    val activities: String,
    val link: String
)

/**
 * DTO for applying partial updates to internships.
 * Input: sent from client to backend.
 */
data class InternshipPatchDTO(
    val title: String? = null,
    val imageUrl: String? = null,
    val publicationDate: Date? = null,
    val duration: String? = null,
    val salary: Double? = null,
    val modality: ModalityRequest? = null,
    val schedule: String? = null,
    val requirements: String? = null,
    val activities: String? = null,
    val link: String? = null,
    val companyId: Long? = null,
    val locationId: Long? = null
)



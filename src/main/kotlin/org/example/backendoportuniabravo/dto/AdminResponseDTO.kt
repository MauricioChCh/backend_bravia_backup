package org.example.backendoportuniabravo.dto

data class AdminResponseDTO(
    val id: Long,
    val profileId: Long,
    val fullName: String,
    val email: String,
)
package org.example.backendoportuniabravo.dto

data class AdminRequestDTO(
    val profileId: Long
)

data class AdminBanningStudentRequestDTO(

    val userId: Long,
    val bannStatus: Boolean

)
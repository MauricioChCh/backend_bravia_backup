package org.example.backendoportuniabravo.dto

data class ReportActionResponseDTO(
    val id: Long,
    val userReportId: Long,
    val reportedUserName: String,
    val adminId: Long,
    val adminName: String,
    val action: String
)
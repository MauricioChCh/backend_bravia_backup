package org.example.backendoportuniabravo.dto

data class ReportActionRequestDTO(
    val userReportId: Long,
    val adminId: Long,
    val action: String
)
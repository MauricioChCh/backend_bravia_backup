package org.example.backendoportuniabravo.dto

class UserReportResponseDTO (
    val id: Long,
    val reporterId: Long,
    val reporterName: String,
    val reportedUserId: Long,
    val reportedUserName: String,
    val description: String
)
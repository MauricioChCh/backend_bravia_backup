package org.example.backendoportuniabravo.dto

data class AuthResponseDto(
    val token: String,
    val userId: String,
    val username: String,
    val authorities: List<AuthorityDto>
)

data class AuthorityDto(
    val authority: String
)

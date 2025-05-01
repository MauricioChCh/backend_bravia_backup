package org.example.backendoportuniabravo.DTOs

data class CompanyUserInput(
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var companyName: String? = null,
    var businessAreaId: Long? = null
)

data class CompanyUserUpdate(
    var id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var companyName: String? = null,
    var businessAreaId: Long? = null
)
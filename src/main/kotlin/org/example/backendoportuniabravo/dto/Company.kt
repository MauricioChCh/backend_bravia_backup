package org.example.backendoportuniabravo.dto


data class TagDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class BusinessAreaDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class ContactDetails(
    var id: Long? = null,
    var url: String? = null,
)

data class CompanyUserInput(
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var companyName: String? = null,
    var businessAreaId: BusinessAreaDetails? = null
)

data class CompanyUserResult(
    var id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var companyName: String? = null,
    var businessAreaId: BusinessAreaDetails? = null
)

data class CompanyUserUpdate(
    var id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var companyName: String? = null,
    var description: String? = null,
    var businessArea: List<BusinessAreaDetails>? = null,
    var tags: List<TagDetails>? = null,
    var location: LocationDetails? = null,
    var contacts: List<ContactDetails>? = null,
)

data class CompanyUserResponse(
    var id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var companyName: String? = null,
    var description: String? = null,
    var businessArea: List<BusinessAreaDetails>? = null,
    var tags: List<TagDetails>? = null,
    var location: LocationDetails? = null,
    var contacts: List<ContactDetails>? = null,
)
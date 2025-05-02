package org.example.backendoportuniabravo.dto


data class TagDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class TagInput(
    var id: Long? = null,
)

data class BusinessAreaDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class BusinessAreaInput(
    var id: Long? = null,
)

data class ContactDetails(
    var id: Long? = null,
    var url: String? = null,
)

data class ContactInput(
    var url: String? = null,
)

data class CompanyUserInput(
    var user: UserInput? = null,
    var name: String? = null,
    var businessArea: BusinessAreaDetails? = null
)

data class CompanyUserResult(
    var id: Long? = null,
    var name: String? = null,
    var user: UserResult? = null,
)

data class CompanyUserUpdate(
    var id: Long? = null,
    var user: UserUpdate? = null,
    var name: String? = null,
    var description: String? = null,
    var businessAreas: List<BusinessAreaInput>? = null,
    var tags: List<TagInput>? = null,
    var location: LocationInput? = null,
    var contacts: List<ContactInput>? = null,
)

data class CompanyUserResponse(
    var id: Long? = null,
    var user: UserResult? = null,
    var name: String? = null,
    var description: String? = null,
    var businessAreas: List<BusinessAreaDetails>? = null,
    var tags: List<TagDetails>? = null,
    var location: LocationDetails? = null,
    var contacts: List<ContactDetails>? = null,
)


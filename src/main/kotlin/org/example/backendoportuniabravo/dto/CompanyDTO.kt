package org.example.backendoportuniabravo.dto

import java.util.*


// response DTO
data class CompanyUserResponse(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var businessAreas: List<BusinessAreaDetails>? = null,
    var tags: List<TagDetails>? = null,
    var location: LocationDetails? = null,
    var contacts: List<ContactDetails>? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var verified: Boolean? = false,
)

// result DTO
data class CompanyUserResult(
    var id: Long? = null,
    var name: String? = null,
    var user: UserResult? = null,
)
data class CompanyDescriptionResult(
    var id: Long? = null,
    var description: String? = null,
)
data class CompanyNameResult(
    var id: Long? = null,
    var name: String? = null,
)
data class CompanyTagsResult(
    var tags: List<TagDetails>? = null,
)
data class CompanyBusinessAreaResult(
    var businessAreas: List<BusinessAreaDetails>? = null,
)
data class CompanyContactsResult(
    var contacts: List<ContactDetails>? = null,
)
data class CompanyImageResult(
    var imageUrl: String? = null,
) // TODO: No se si va a funcionar asi


// details DTO
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

// update DTO
data class CompanyDescriptionUpdate(
    var id: Long? = null,
    var description: String? = null,
)
data class CompanyNameUpdate(
    var id: Long? = null,
    var name: String? = null,
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
data class CompanyTagsUpdate(
    var tags: List<TagInput>? = null,
)
data class CompanyBusinessAreaUpdate(
    var businessAreas: List<BusinessAreaInput>? = null,
)
data class CompanyImageUpdate(
    var imageUrl: String? = null,
)

// input DTO
data class ContactInput(
    var url: String? = null,
)
data class BusinessAreaInput(
    var id: Long? = null,
)
data class CompanyUserInput(
    var user: UserInput? = null,
    var name: String? = null,
    var businessArea: BusinessAreaInput? = null
)
data class TagInput(
    var id: Long? = null,
)














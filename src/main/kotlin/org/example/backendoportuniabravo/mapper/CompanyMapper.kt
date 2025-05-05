package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.mapstruct.*
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Contact
import org.example.backendoportuniabravo.entity.Location
import org.example.backendoportuniabravo.entity.User

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = [UserMapper::class]
)
interface CompanyMapper {

  @Mapping(target = "profile", ignore = true)
  fun companyUserInputToCompany(companyUserInput: CompanyUserInput): Company

  @Mapping(target = "createDate", ignore = true)
  fun companyUserInputToUser(companyUserInput: CompanyUserInput): User

  @Mapping(target = "user", source = "profile.user")
  fun companyToCompanyUserResult(company: Company): CompanyUserResult

  @Mapping(target = "tags",   source = "company.tags")
  fun companyToCompanyTagsResult(company: Company): CompanyTagsResult

  @Mapping(target = "businessAreas", source = "company.businessAreas")
  fun companyToCompanyBusinessAreaResult(company: Company): CompanyBusinessAreaResult

  @Mapping(target = "contacts", source = "company.contacts")
  fun companyToCompanyContactsResult(company: Company): CompanyContactsResult

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "company", ignore = true)
  fun companyContactInputToContact(contactInput: ContactInput): Contact

  @Mapping(target = "id", source = "company.location.id")
  @Mapping(target = "address", source = "company.location.address")
  @Mapping(target = "country", source = "company.location.country")
  @Mapping(target = "city", source = "company.location.city")
  fun companyToLocationResult(company: Company): LocationResult

  fun companyLocationInputToLocation(locationInput: LocationInput): Location

  @Mapping(target = "country.name", ignore = true)
  fun companyLocationUpdateToLocation(locationUpdate: LocationUpdate): Location


  fun companyNameUpdateToCompany(companyNameUpdate: CompanyNameUpdate): Company
  fun companyToCompanyNameResult(company: Company): CompanyNameResult

  fun companyDescriptionUpdateToCompany(companyDescriptionUpdate: CompanyDescriptionUpdate): Company
  fun companyToCompanyDescriptionResult(company: Company): CompanyDescriptionResult



  fun companyToCompanyUserResponse(company: Company): CompanyUserResponse
  fun companyUserUpdateToCompany(companyUserUpdate: CompanyUserUpdate): Company
}


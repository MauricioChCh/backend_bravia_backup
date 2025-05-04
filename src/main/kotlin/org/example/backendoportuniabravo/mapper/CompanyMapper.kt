package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.mapstruct.*
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Contact

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = [UserMapper::class]
)
interface CompanyMapper {

  @Mapping(target = "profile", ignore = true)
  fun companyUserInputToCompany(companyUserInput: CompanyUserInput): Company

  // mapping for the companyUserInput to company
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
  fun contactInputToContact(contactInput: ContactInput): Contact

  fun companyNameUpdateToCompany(companyNameUpdate: CompanyNameUpdate): Company
  fun companyToCompanyNameResult(company: Company): CompanyNameResult

  fun companyDescriptionUpdateToCompany(companyDescriptionUpdate: CompanyDescriptionUpdate): Company
  fun companyToCompanyDescriptionResult(company: Company): CompanyDescriptionResult



  fun companyToCompanyUserResponse(company: Company): CompanyUserResponse
  fun companyUserUpdateToCompany(companyUserUpdate: CompanyUserUpdate): Company
}


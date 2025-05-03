package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.mapstruct.*
import org.example.backendoportuniabravo.entity.Company

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


  fun companyNameUpdateToCompany(companyNameUpdate: CompanyNameUpdate): Company
  fun companyToCompanyNameResult(company: Company): CompanyNameResult

  fun companyDescriptionUpdateToCompany(companyDescriptionUpdate: CompanyDescriptionUpdate): Company
  fun companyToCompanyDescriptionResult(company: Company): CompanyDescriptionResult



  fun companyToCompanyUserResponse(company: Company): CompanyUserResponse
  fun companyUserUpdateToCompany(companyUserUpdate: CompanyUserUpdate): Company
}


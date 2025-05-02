package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.mapstruct.*
import org.example.backendoportuniabravo.entity.BusinessArea
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Tag
import java.time.LocalDateTime

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TagMapper {
  fun tagToTagDetails(tag: Tag): TagDetails
  fun tagListToTagDetailsList(tagList: List<Tag>): List<TagDetails>
  fun tagDetailsToTag(tagDetails: TagDetails): Tag
  fun tagDetailsListToTag(tagDetailsList: List<TagDetails>): List<Tag>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BusinessAreaMapper {
  fun businessAreaToBusinessAreaDetails(businessArea: BusinessArea): BusinessAreaDetails

  fun businessAreaListToBusinessAreaDetailsList(businessAreaList: List<BusinessArea>): List<BusinessAreaDetails>

  fun businessAreaDetailsToBusinessArea(businessAreaDetails: BusinessAreaDetails): BusinessArea

  fun businessAreaDetailsListToBusinessArea(businessAreaDetailsList: List<BusinessAreaDetails>): List<BusinessArea>
}

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

  fun companyNameUpdateToCompany(companyNameUpdate: CompanyNameUpdate): Company
  fun companyDescriptionUpdateToCompany(companyDescriptionUpdate: CompanyDescriptionUpdate): Company
  fun companyToCompanyDescriptionResult(company: Company): CompanyDescriptionResult
  fun companyToCompanyNameResult(company: Company): CompanyNameResult


  fun companyToCompanyUserResponse(company: Company): CompanyUserResponse
  fun companyUserUpdateToCompany(companyUserUpdate: CompanyUserUpdate): Company
}


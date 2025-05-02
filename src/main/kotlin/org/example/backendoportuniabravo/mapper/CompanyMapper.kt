package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.BusinessAreaDetails
import org.example.backendoportuniabravo.dto.CompanyUserInput
import org.example.backendoportuniabravo.dto.CompanyUserResponse
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.dto.CompanyUserUpdate
import org.mapstruct.*
import org.example.backendoportuniabravo.dto.TagDetails
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

  fun companyToCompanyUserResponse(company: Company): CompanyUserResponse
  fun companyUserUpdateToCompany(companyUserUpdate: CompanyUserUpdate): Company
}


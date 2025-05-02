package org.example.backendoportuniabravo.mappers

import org.example.backendoportuniabravo.dto.BusinessAreaDetails
import org.example.backendoportuniabravo.dto.CompanyUserInput
import org.example.backendoportuniabravo.dto.CompanyUserResponse
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.dto.CompanyUserUpdate
import org.mapstruct.*
import org.example.backendoportuniabravo.dto.TagDetails
import org.example.backendoportuniabravo.entities.BusinessArea
import org.example.backendoportuniabravo.entities.Company
import org.example.backendoportuniabravo.entities.Tag

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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CompanyMapper {
  fun companyUserInputToCompany(companyUserInput: CompanyUserInput): Company
  fun companyUserUpdateToCompany(companyUserUpdate: CompanyUserUpdate): Company
  fun companyToCompanyUserResponse(company: Company): CompanyUserResponse
  fun companyToCompanyUserResult(company: Company): CompanyUserResult
}


package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.ReportActionRequestDTO
import org.example.backendoportuniabravo.dto.ReportActionResponseDTO
import org.example.backendoportuniabravo.entity.Admin
import org.example.backendoportuniabravo.entity.ReportAction
import org.example.backendoportuniabravo.entity.UserReports
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.util.Optional

@Mapper(componentModel = "spring")
interface ReportActionMapper {

    @Mapping(source = "reportAction.id", target = "id")
    @Mapping(source = "reportAction.userReport.id", target = "userReportId")
    @Mapping(
        expression = "java(reportAction.getUserReport().getUserReported().getFirstName() + \" \" + reportAction.getUserReport().getUserReported().getLastName())",
        target = "reportedUserName"
    )
    @Mapping(source = "reportAction.admin.id", target = "adminId")
    @Mapping(
        expression = "java(reportAction.getAdmin().getProfile().getUser().getFirstName() + \" \" + reportAction.getAdmin().getProfile().getUser().getLastName())",
        target = "adminName"
    )
    @Mapping(source = "reportAction.action", target = "action")
    fun toResponseDto(reportAction: ReportAction): ReportActionResponseDTO

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userReport", source = "userReport")
    @Mapping(target = "admin", source = "admin")
    @Mapping(target = "action", source = "dto.action")
    fun toEntity(
        dto: ReportActionRequestDTO,
        admin: Admin,
        userReport: UserReports
    ): ReportAction

}


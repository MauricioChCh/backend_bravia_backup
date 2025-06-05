package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.UserReportRequestDTO
import org.example.backendoportuniabravo.dto.UserReportResponseDTO
import org.example.backendoportuniabravo.entity.User
import org.example.backendoportuniabravo.entity.UserReports
import org.mapstruct.Mapper
import org.mapstruct.Mapping


@Mapper(componentModel = "spring")
interface UserReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "reporter")
    @Mapping(target = "userReported", source = "userReported")
    @Mapping(target = "description", source = "dto.description")
    fun toEntity(dto: UserReportRequestDTO, reporter: User, userReported: User): UserReports

    @Mapping(target = "reporterId", source = "user.id")
    @Mapping(target = "reporterName", expression = "java(report.getUser().getFirstName() + \" \" + report.getUser().getLastName())")
    @Mapping(target = "reportedUserId", source = "userReported.id")
    @Mapping(target = "reportedUserName", expression = "java(report.getUserReported().getFirstName() + \" \" + report.getUserReported().getLastName())")
    fun toResponseDTO(report: UserReports): UserReportResponseDTO

    fun toResponseDTOList(reportList: List<UserReports>): List<UserReportResponseDTO>
}
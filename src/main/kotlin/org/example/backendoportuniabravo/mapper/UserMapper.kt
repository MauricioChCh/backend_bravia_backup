package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.UserInput
import org.example.backendoportuniabravo.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.time.LocalDateTime

@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
interface UserMapper {

    fun userInputToUser(userInput: UserInput): User
    fun userToUserResult(user: org.example.backendoportuniabravo.entity.User): org.example.backendoportuniabravo.dto.UserResult
    fun userToCompanyUserResponse(user: org.example.backendoportuniabravo.entity.User): org.example.backendoportuniabravo.dto.CompanyUserResponse
    fun userToCompanyUserResult(user: org.example.backendoportuniabravo.entity.User): org.example.backendoportuniabravo.dto.CompanyUserResult
}
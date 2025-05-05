package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.CompanyUserResponse
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.dto.UserInput
import org.example.backendoportuniabravo.dto.UserResult
import org.example.backendoportuniabravo.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.time.LocalDateTime

@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
interface UserMapper {

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "tokenExpired", ignore = true)
    fun userInputToUser(userInput: UserInput): User
    fun userToUserResult(user: User): UserResult
    fun userToCompanyUserResponse(user: User): CompanyUserResponse
    fun userToCompanyUserResult(user: User): CompanyUserResult
}
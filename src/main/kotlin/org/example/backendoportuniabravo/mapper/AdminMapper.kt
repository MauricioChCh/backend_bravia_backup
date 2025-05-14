package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.AdminRequestDTO
import org.example.backendoportuniabravo.dto.AdminResponseDTO
import org.example.backendoportuniabravo.entity.Admin
import org.example.backendoportuniabravo.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface AdminMapper{
    fun adminToAdminRequestDTO(admin: Admin): AdminRequestDTO
    fun adminRequestDTOToAdmin(adminRequestDTO: AdminRequestDTO): Admin
    fun adminToAdminResponseDTO(admin: Admin): AdminResponseDTO
    fun adminResponseDTOToAdmin(adminResponseDTO: AdminResponseDTO): Admin
    fun adminListToAdminResponseDTOList(adminList: List<Admin>): List<AdminResponseDTO>
    fun adminResponseDTOListToAdminList(dtoList: List<AdminResponseDTO>): List<Admin>
    @Mapping(target = "id", source = "admin.id")
    @Mapping(target = "fullName",
        expression = "java(user != null ? user.getFirstName() + \" \" + user.getLastName() : \"Nombre no disponible\")")
    fun adminToAdminResponseDTOWithFullName(admin: Admin, user: User?): AdminResponseDTO

}
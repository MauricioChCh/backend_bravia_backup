package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.ReportAction


interface AdminService{
    fun getAdmins() : List<AdminResponseDTO>?
    fun getAdminById(id: Long) : AdminResponseDTO?
    fun addAdmin(id: Long) : Long?
    fun deleteAdmin(profileId: Long) : Boolean
    fun getAllCompanies(): List<CompanyUserResponse>


}

interface ReportActionService {

    fun getAllActions(): List<ReportAction>

    fun getActionById(id: Long): ReportAction?

    fun getActionsByAdminId(adminId: Long): List<ReportAction>

    fun existsByAdminId(adminId: Long): Boolean

    fun getActionByUserReportId(userReportId: Long): ReportAction?

    fun existsByUserReportId(userReportId: Long): Boolean

    fun searchByActionText(action: String): List<ReportAction>

    fun createAction(dto: ReportActionRequestDTO): ReportAction

    fun deleteAction(id: Long)
}




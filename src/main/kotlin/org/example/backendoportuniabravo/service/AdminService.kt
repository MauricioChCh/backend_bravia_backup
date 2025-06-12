package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.ReportAction
import org.example.backendoportuniabravo.entity.Student


interface AdminService{
    fun getAdmins() : List<AdminResponseDTO>?
    fun getAdminById(id: Long) : AdminResponseDTO?
    fun addAdmin(id: Long) : Long?
    fun deleteAdmin(profileId: Long) : Boolean
    fun getAllCompanies(): List<CompanyUserResponse>
    fun getAllStudents(): List<StudentResponseDTO>
    fun getStudentById(studentId: Long): StudentResponseDTO?
    fun getCompanyById(companyId: Long): CompanyUserResponse?       // ← agregado
    fun getCompanyByUserId(userId: Long): CompanyUserResponse?
    fun getStudentByUserId(userId: Long): StudentResponseDTO?
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




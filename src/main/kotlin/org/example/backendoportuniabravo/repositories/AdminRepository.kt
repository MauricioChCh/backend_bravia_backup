package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.Admin
import org.example.backendoportuniabravo.entities.ReportAction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository: JpaRepository <Admin, Long>{

    fun findByProfileId(profileId: Long): Admin?

    fun existsByProfileId(profileId: Long): Boolean

}

@Repository
interface ReportActionRepository : JpaRepository<ReportAction, Long>{

    fun findByAdminId(adminId: Long): List<ReportAction>

    fun existsByAdminId(adminId: Long): Boolean

    fun findByUserReportId(reportId: Long): ReportAction?

    fun existsByUserReportId(userReportId: Long): Boolean

    fun findByActionContainingIgnoreCase(action: String): List<ReportAction>

}
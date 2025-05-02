package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.UserReports
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserReportRepository: JpaRepository <UserReports, Long>{

    fun findByUserId(userId: Long): UserReports?

    fun existsByUserId(userId: Long): Boolean

    fun findByUserReportedId(userReportedId: Long): UserReports?

    fun existsByUserReportedId(userReportedId: Long): Boolean

    fun findByDescriptionContainingIgnoreCase(description: String): List<UserReports>

    //fun findAllByOrderByReportDateDesc(): List<UserReports>
}
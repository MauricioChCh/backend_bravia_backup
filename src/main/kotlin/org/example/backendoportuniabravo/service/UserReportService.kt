package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.entity.UserReports
import org.example.backendoportuniabravo.repository.UserReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserReportService {
    fun findAll(): List<UserReports>
    fun findById(id: Long): UserReports
    fun deleteById(id: Long)
    fun save(userReport: UserReports): UserReports
    fun update(userReport: UserReports): UserReports
}



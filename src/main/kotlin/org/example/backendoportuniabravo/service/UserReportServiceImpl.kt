package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.entity.UserReports
import org.example.backendoportuniabravo.repository.UserReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserReportServiceImpl(
    private val userReportRepository: UserReportRepository
) : UserReportService {

    override fun findAll(): List<UserReports> {
        return userReportRepository.findAll()
    }

    override fun findById(id: Long): UserReports {
        return userReportRepository.findById(id)
            .orElseThrow { RuntimeException("UserReport with ID $id not found") }
    }

    @Transactional
    override fun deleteById(id: Long) {
        if (!userReportRepository.existsById(id)) {
            throw RuntimeException("UserReport with ID $id not found")
        }
        userReportRepository.deleteById(id)
    }

    @Transactional
    override fun save(userReport: UserReports): UserReports {
        return userReportRepository.save(userReport)
    }

    @Transactional
    override fun update(userReport: UserReports): UserReports {
        val existing = userReportRepository.findById(userReport.id!!)
            .orElseThrow { RuntimeException("UserReport with ID ${userReport.id} not found") }

        existing.description = userReport.description

        return userReportRepository.save(existing)
    }
}
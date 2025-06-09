package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.MarkedInternship
import org.example.backendoportuniabravo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MarkedInternshipRepository : JpaRepository<MarkedInternship, Long> {
    fun findByUserId(userId: Long): List<MarkedInternship>
    fun findByInternshipIdAndUserId(internshipId: Long, userId: Long): MarkedInternship?
}
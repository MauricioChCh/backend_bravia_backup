package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.BusinessArea
import org.example.backendoportuniabravo.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BusinessAreaRepository : JpaRepository<BusinessArea, Long> {
    fun findByNameIgnoreCase(name: String): Optional<BusinessArea>
}


@Repository
interface CompanyRepository : JpaRepository<Company, Long>
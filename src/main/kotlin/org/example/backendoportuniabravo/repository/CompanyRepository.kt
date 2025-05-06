package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.BusinessArea
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Contact
import org.example.backendoportuniabravo.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BusinessAreaRepository : JpaRepository<BusinessArea, Long> {
    fun findByNameIgnoreCase(name: String): Optional<BusinessArea>
}

@Repository
interface TagRepository : JpaRepository<Tag, Long>

@Repository
interface ContactRepository : JpaRepository<Contact, Long>

@Repository
interface CompanyRepository : JpaRepository<Company, Long>{
    fun deleteCompanyById(id: Long): Unit
}
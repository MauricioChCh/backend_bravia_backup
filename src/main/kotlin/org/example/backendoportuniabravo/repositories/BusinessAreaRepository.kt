package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.BusinessArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BusinessAreaRepository : JpaRepository<BusinessArea, Long> {
    fun findByNameIgnoreCase(name: String): Optional<BusinessArea>
}
package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository: JpaRepository <Admin, Long>{

    fun findByProfileId(profileId: Long): Admin?

}
package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.Modality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ModalityRepository : JpaRepository<Modality, Long> {
    /**
     * Finds a Modality by its name.
     *
     * @param name the name of the Modality to find.
     * @return the Modality with the specified name, or null if not found.
     */
    fun findByName(name: String): Modality?
}
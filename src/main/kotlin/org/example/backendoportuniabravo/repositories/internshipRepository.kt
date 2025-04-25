package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.Internship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface InternshipRepository : JpaRepository<Internship, Long> {

    // Búsqueda básica por ID (ya viene con JpaRepository)
    // Optional<Internship> findById(Long id)

    // Buscar pasantías por título (búsqueda parcial)
    fun findByTitleContainingIgnoreCase(title: String): List<Internship>

    // Buscar pasantías por ubicación
//    fun findByLocationIgnoreCase(location: String): List<Internship>
//
    // Buscar pasantías por modalidad
    fun findByModalityIgnoreCase(modality: String): List<Internship>

    // Buscar pasantías por área de negocio
//    fun findByBusinessAreasId(businessAreaId: Long): List<Internship>

    // Obtener pasantías más recientes primero
    fun findAllByOrderByPublicationDateDesc(): List<Internship>

    // Obtener pasantías aleatorias (alternativa simple)
    @Query("SELECT i FROM Internship i ORDER BY RANDOM() LIMIT :limit")
    fun findRandomInternships(limit: Int): List<Internship>
}

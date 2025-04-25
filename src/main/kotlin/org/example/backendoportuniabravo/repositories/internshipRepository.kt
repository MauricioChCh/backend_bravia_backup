package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.Internship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface InternshipRepository : JpaRepository<Internship, Long> {

    // Buscar pasantías por título (búsqueda parcial)
    fun findByTitleContainingIgnoreCase(title: String): List<Internship>

    // Obtener pasantías más recientes primero
    fun findAllByOrderByPublicationDateDesc(): List<Internship>

    // Buscar pasantías por modalidad
    fun findByModalityIgnoreCase(modality: String): List<Internship>

    // Buscar pasantías por ubicación
    fun findByLocation_Province_NameContainingIgnoreCase(provinceName: String): List<Internship>
    fun findByLocation_Country_NameContainingIgnoreCase(countryName: String): List<Internship>
    fun findByLocation_AddressContainingIgnoreCase(address: String): List<Internship>


    // Buscar pasantías por área de negocio
//    fun findByBusinessAreasId(businessAreaId: Long): List<Internship>


    // Obtener pasantías aleatorias (alternativa simple) de momento antes de hacer las recomendadas
    @Query("SELECT i FROM Internship i ORDER BY RANDOM() LIMIT :limit")
    fun findAllRecommended(limit: Int): List<Internship>
}

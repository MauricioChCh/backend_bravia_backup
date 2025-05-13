package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO

/**
 * Service layer for managing Internship operations.
 */
interface InternshipService {

    /**
     * Creates a new internship from the given data.
     * @param dto the internship creation data
     * @return the created internship as a response DTO
     */
    fun createInternship(dto: InternshipRequestDTO): InternshipResponseDTO

    /**
     * Retrieves all internships in the system.
     * @return a list of internships
     */
    fun getAll(): List<InternshipResponseDTO>

    /**
     * Updates a complete internship by its ID.
     * @param id the ID of the internship to update
     * @param dto the new internship data
     * @return the updated internship as a response DTO
     */
    fun update(id: Long, dto: InternshipRequestDTO): InternshipResponseDTO

    /**
     * Finds an internship by its ID.
     * @param id the ID of the internship
     * @return the corresponding internship response DTO
     */
    fun findById(id: Long): InternshipResponseDTO

    /**
     * Searches internships by a keyword matching title or requirements.
     * @param query the search keyword
     * @return a list of matching internships
     */
    fun search(query: String): List<InternshipResponseDTO>

    /**
     * Recommends internships to a student based on their interests.
     * @param studentId the student's ID
     * @return a list of recommended internships
     */
    fun getRecommendedForStudent(studentId: Long): List<InternshipResponseDTO>

    /**
     * Applies partial updates to an internship by its ID.
     * @param id the ID of the internship
     * @param dto the partial update data
     * @return the updated internship
     */
    fun patch(id: Long, dto: InternshipPatchDTO): InternshipResponseDTO
}

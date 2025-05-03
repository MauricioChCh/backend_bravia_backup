package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO



interface InternshipService{
    fun createInternship(dto: InternshipRequestDTO): InternshipResponseDTO
    fun getAll(): List<InternshipResponseDTO>
    fun update(id: Long, dto: InternshipRequestDTO): InternshipResponseDTO
    fun findById(id: Long): InternshipResponseDTO
    fun search(query: String): List<InternshipResponseDTO>
    fun getRecommendedForStudent(studentId: Long): List<InternshipResponseDTO>
    fun patch(id: Long, dto: InternshipPatchDTO): InternshipResponseDTO

}

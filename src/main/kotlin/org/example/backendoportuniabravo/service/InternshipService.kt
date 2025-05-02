package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO



interface InternshipService{
    fun createInternship(dto: InternshipRequestDTO): InternshipResponseDTO
    fun getAll(): List<InternshipResponseDTO>
    fun update(id: Long, dto: InternshipRequestDTO): InternshipResponseDTO

}

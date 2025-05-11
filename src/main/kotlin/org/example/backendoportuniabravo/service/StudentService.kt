package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO

interface StudentService {
    fun create(dto: StudentRequestDTO): StudentResponseDTO
    fun findById(id: Long): StudentResponseDTO
    fun findAll(): List<StudentResponseDTO>
    fun delete(id: Long)
    fun update(id: Long, dto: StudentRequestDTO): StudentResponseDTO
}

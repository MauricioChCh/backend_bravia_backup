package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*

interface StudentService {
    fun create(dto: StudentCreateRequestDTO): StudentResponseDTO
    fun findById(id: Long): StudentResponseDTO
    fun findAll(): List<StudentResponseDTO>
    fun delete(id: Long)
    fun update(id: Long, dto: StudentRequestDTO): StudentResponseDTO
    fun returnStudentCurriculum(id: Long): StudentCurriculumResponseDTO
    fun registerStudent(dto: StudentRegister): StudentResponseDTO
}

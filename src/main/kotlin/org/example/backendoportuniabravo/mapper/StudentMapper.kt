package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.Student
import org.mapstruct.*

interface StudentMapper {
    fun toDto(student: Student): StudentResponseDTO

    fun toDtoList(list: List<Student>): List<StudentResponseDTO>

    fun toEntity(dto: StudentRequestDTO, profile: Profile): Student

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateFromDto(dto: StudentRequestDTO, @MappingTarget student: Student)
}
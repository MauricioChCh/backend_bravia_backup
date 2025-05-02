package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.entities.Profile
import org.example.backendoportuniabravo.entities.Student
import org.mapstruct.*

@Mapper(componentModel = "spring")
interface StudentMapper {

//    @Mapping(source = "profile.fullName", target = "fullName")
    fun toDto(student: Student): StudentResponseDTO

    fun toDtoList(list: List<Student>): List<StudentResponseDTO>


    @Mapping(target = "id", ignore = true)
    fun toEntity(dto: StudentRequestDTO, profile: Profile): Student

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateFromDto(dto: StudentRequestDTO, @MappingTarget student: Student)
}

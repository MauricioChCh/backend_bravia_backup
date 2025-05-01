package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.entities.Internship
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface InternshipMapper {

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "location.province.name", target = "provinceName")
    @Mapping(source = "location.country.name", target = "countryName")
    fun toDto(internship: Internship): InternshipResponseDTO

    fun toEntity(dto: InternshipRequestDTO): Internship
}

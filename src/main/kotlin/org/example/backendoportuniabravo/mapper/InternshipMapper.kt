package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.entities.Internship
import org.mapstruct.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Mapper(componentModel = "spring")
interface InternshipMapper {

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "location.province.name", target = "provinceName")
    @Mapping(source = "location.country.name", target = "countryName")
    fun toDto(internship: Internship): InternshipResponseDTO

    fun toDtoList(list: List<Internship>): List<InternshipResponseDTO>

    @Mapping(target = "id", ignore = true)
    fun toEntity(dto: InternshipRequestDTO): Internship

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateFromDto(dto: InternshipRequestDTO, @MappingTarget entity: Internship)




}

package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.Internship
import org.example.backendoportuniabravo.entity.Modality
import org.mapstruct.*

@Mapper(componentModel = "spring")
interface InternshipMapper {

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "location.city.name", target = "cityName")
    @Mapping(source = "location.country.name", target = "countryName")
    @Mapping(
        target = "locationFullName",
        expression = "java(internship.getLocation().getCity().getName() + \", \" + internship.getLocation().getCountry().getName())"
    )
    fun toDto(internship: Internship): InternshipResponseDTO

   // fun toDtoList(list: List<Internship>): List<InternshipResponseDTO>

//    @Mapping(target = "id", ignore = true)
//    fun toEntity(dto: InternshipRequestDTO): Internship

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "modality", target = "modality.id")
    fun updateFromDto(dto: InternshipRequestDTO, @MappingTarget entity: Internship)


    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "location.city.name", target = "cityName")
    @Mapping(source = "location.country.name", target = "countryName")
    @Mapping(source = "bookmarked", target = "isBookmarked", defaultValue = "false")
    fun internshipToInternshipResponseDTO(internship: Internship): InternshipResponseDTO

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "location.city.name", target = "cityName")
    @Mapping(source = "location.country.name", target = "countryName")
    @Mapping(source = "bookmarked", target = "isBookmarked", defaultValue = "false")
    @Mapping(source = "modality.name", target = "modality")
    fun internshipTOInternshipResponse(internship: Internship): InternshipResponse

}


@Mapper(componentModel = "spring")
interface ModalityMapper {

    fun modalityRequestTOModality(dto: ModalityRequest): Modality

    fun modalityToModalityResponse(dto: Modality) : ModalityResponse

}
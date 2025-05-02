package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.entity.Internship
import org.example.backendoportuniabravo.mapper.InternshipMapper
import org.example.backendoportuniabravo.repository.CompanyRepository
import org.example.backendoportuniabravo.repository.InternshipRepository
import org.example.backendoportuniabravo.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class InternshipService(
    private val internshipRepository: InternshipRepository,
    private val companyRepository: CompanyRepository,
    private val locationRepository: LocationRepository,
    private val mapper: InternshipMapper
) {

    fun createInternship(dto: InternshipRequestDTO): InternshipResponseDTO {
        val company = companyRepository.findById(dto.companyId)
            .orElseThrow { IllegalArgumentException("Company not found") }
        val location = locationRepository.findById(dto.locationId)
            .orElseThrow { IllegalArgumentException("Location not found") }

        val internship = Internship(
            title = dto.title,
            imageUrl = dto.imageUrl,
            publicationDate = dto.publicationDate,
            duration = dto.duration,
            salary = dto.salary,
            modality = dto.modality,
            schedule = dto.schedule,
            requirements = dto.requirements,
            activities = dto.activities,
            link = dto.link,
            company = company,
            location = location
        )

        val saved = internshipRepository.save(internship)
        return mapper.toDto(saved)
    }


    fun getAll(): List<InternshipResponseDTO> =
        internshipRepository.findAll().map { mapper.toDto(it) }
}

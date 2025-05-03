package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.entities.Internship
import org.example.backendoportuniabravo.mapper.InternshipMapper
import org.example.backendoportuniabravo.repositories.CompanyRepository
import org.example.backendoportuniabravo.repositories.InternshipRepository
import org.example.backendoportuniabravo.repositories.LocationRepository
import org.example.backendoportuniabravo.repositories.StudentRepository
import org.springframework.stereotype.Service

@Service
class InternshipServiceImpl (
    private val studentRepository: StudentRepository,
    private val internshipRepository: InternshipRepository,
    private val companyRepository: CompanyRepository,
    private val locationRepository: LocationRepository,
    private val mapper: InternshipMapper
): InternshipService {
    override fun createInternship(dto: InternshipRequestDTO): InternshipResponseDTO {
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


    override fun getAll(): List<InternshipResponseDTO> =
        internshipRepository.findAll().map { mapper.toDto(it) }

    override fun update(id: Long, dto: InternshipRequestDTO): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { RuntimeException("Internship not found") }

        mapper.updateFromDto(dto, internship)
        return mapper.toDto(internshipRepository.save(internship))
    }

    override fun findById(id: Long): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { RuntimeException("Internship not found") }
        return mapper.toDto(internship)
    }

    override fun search(query: String): List<InternshipResponseDTO> {
        return internshipRepository
            .searchByTitleOrRequirements(query.lowercase())
            .map(mapper::toDto)
    }
    override fun getRecommendedForStudent(studentId: Long): List<InternshipResponseDTO> {
        val student = studentRepository.findById(studentId)
            .orElseThrow { RuntimeException("Student not found") }

        val interests = student.interests.map { it.name.lowercase() }

        val matchingInternships = internshipRepository
            .findAll() // se puede optimizar
            .filter { i ->
                interests.any { interest -> i.requirements.lowercase().contains(interest) }
            }
            .sortedByDescending { it.publicationDate } // o .salary, etc.
            .take(5)

        return matchingInternships.map(mapper::toDto)
    }

    override fun patch(id: Long, dto: InternshipPatchDTO): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { RuntimeException("Internship not found") }

        dto.title?.let { internship.title = it }
        dto.imageUrl?.let { internship.imageUrl = it }
        dto.publicationDate?.let { internship.publicationDate = it }
        dto.duration?.let { internship.duration = it }
        dto.salary?.let { internship.salary = it }
        dto.modality?.let { internship.modality = it }
        dto.schedule?.let { internship.schedule = it }
        dto.requirements?.let { internship.requirements = it }
        dto.activities?.let { internship.activities = it }
        dto.link?.let { internship.link = it }

        dto.companyId?.let {
            val company = companyRepository.findById(it)
                .orElseThrow { RuntimeException("Company not found") }
            internship.company = company
        }

        dto.locationId?.let {
            val location = locationRepository.findById(it)
                .orElseThrow { RuntimeException("Location not found") }
            internship.location = location
        }

        return mapper.toDto(internshipRepository.save(internship))
    }




}

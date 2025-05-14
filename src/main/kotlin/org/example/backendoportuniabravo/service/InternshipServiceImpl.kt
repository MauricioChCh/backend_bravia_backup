package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.entity.Internship
import org.example.backendoportuniabravo.mapper.InternshipMapper
import org.example.backendoportuniabravo.repository.CompanyRepository
import org.example.backendoportuniabravo.repository.InternshipRepository
import org.example.backendoportuniabravo.repository.LocationRepository
import org.example.backendoportuniabravo.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class InternshipServiceImpl (
    private val studentRepository: StudentRepository,
    private val internshipRepository: InternshipRepository,
    private val companyRepository: CompanyRepository,
    private val locationRepository: LocationRepository,
    private val mapper: InternshipMapper
) : InternshipService {

    /**
     * Crea una nueva pasantía después de validar la existencia de la compañía y la localización.
     */
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

    /**
     * Retorna todas las pasantías registradas en el sistema.
     */
    override fun getAll(): List<InternshipResponseDTO> =
        internshipRepository.findAll().map { mapper.toDto(it) }

    /**
     * Actualiza todos los campos de una pasantía existente.
     */
    override fun update(id: Long, dto: InternshipRequestDTO): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { NoSuchElementException("Internship not found") }

        // Aplica los cambios al objeto existente
        mapper.updateFromDto(dto, internship)
        return mapper.toDto(internshipRepository.save(internship))
    }

    /**
     * Busca una pasantía por ID.
     */
    override fun findById(id: Long): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { NoSuchElementException("Internship not found") }
        return mapper.toDto(internship)
    }

    /**
     * Realiza una búsqueda textual sobre el título o requerimientos de las pasantías.
     */
    override fun search(query: String): List<InternshipResponseDTO> {
        return internshipRepository
            .searchByTitleOrRequirements(query.lowercase())
            .map(mapper::toDto)
    }

    /**
     * Retorna pasantías recomendadas a un estudiante, en base a sus intereses. //sigue en beta
     */
    override fun getRecommendedForStudent(studentId: Long): List<InternshipResponseDTO> {
        val student = studentRepository.findById(studentId)
            .orElseThrow { RuntimeException("Student not found") }

        // Extrae los intereses en minúsculas
        val interests = student.interests.map { it.name.lowercase() }

        // Filtra pasantías que contengan al menos un interés en sus requisitos
        val matchingInternships = internshipRepository
            .findAll() //Podría optimizarse con una consulta en BD
            .filter { i ->
                interests.any { interest -> i.requirements.lowercase().contains(interest) }
            }
            .sortedByDescending { it.publicationDate } // También se puede usar salario u otro criterio
            .take(30)

        return matchingInternships.map(mapper::toDto)
    }

    /**
     * Aplica actualizaciones parciales a una pasantía.
     */
    override fun patch(id: Long, dto: InternshipPatchDTO): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { NoSuchElementException("Internship not found") }

        // Aplica cambios solo si el campo fue proporcionado
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

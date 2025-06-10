package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Internship
import org.example.backendoportuniabravo.entity.MarkedInternship
import org.example.backendoportuniabravo.entity.User
import org.example.backendoportuniabravo.mapper.InternshipMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class InternshipServiceImpl (
    private val studentRepository: StudentRepository,
    private val internshipRepository: InternshipRepository,
    private val companyRepository: CompanyRepository,
    private val locationRepository: LocationRepository,
    private val internshipMapper: InternshipMapper,
    private val markedInternshipRepository: MarkedInternshipRepository
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
        return internshipMapper.toDto(saved)
    }

    /**
     * Retorna todas las pasantías registradas en el sistema.
     */
    override fun getAll(): List<InternshipResponseDTO> =
        internshipRepository.findAll().map { internshipMapper.toDto(it) }

    /**
     * Actualiza todos los campos de una pasantía existente.
     */
    override fun update(id: Long, dto: InternshipRequestDTO): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { NoSuchElementException("Internship not found") }

        // Aplica los cambios al objeto existente
        internshipMapper.updateFromDto(dto, internship)
        return internshipMapper.toDto(internshipRepository.save(internship))
    }

    /**
     * Busca una pasantía por ID.
     */
    override fun findById(id: Long): InternshipResponseDTO {
        val internship = internshipRepository.findById(id)
            .orElseThrow { NoSuchElementException("Internship not found") }
        return internshipMapper.toDto(internship)
    }

    /**
     * Realiza una búsqueda textual sobre el título o requerimientos de las pasantías.
     */
    override fun search(query: String): List<InternshipResponseDTO> {
        return internshipRepository
            .searchByTitleOrRequirements(query.lowercase())
            .map(internshipMapper::toDto)
    }

    /**
     * Retorna pasantías recomendadas a un estudiante, con base en sus intereses. //sigue en beta
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

        return matchingInternships.map(internshipMapper::toDto)
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

        return internshipMapper.toDto(internshipRepository.save(internship))
    }

    override fun bookmarkInternship(internshipId: Long, userId: Long, marked: Boolean) {
        val internship = internshipRepository.findById(internshipId)
            .orElseThrow { NoSuchElementException("Internship not found") }

        val student = studentRepository.findById(userId)
        val company = companyRepository.findById(userId)

        if (student.isEmpty && company.isEmpty) {
            throw RuntimeException("User not found")
        }

        val user: User? = student.orElse(null)?.profile?.user ?: company.orElse(null)?.profile?.user

        if (user == null) {
            throw RuntimeException("User not found")
        } // TODO: se puede quitar

        val existingMarkedInternship = markedInternshipRepository.findByInternshipIdAndUserId(internshipId, user.id!!)

        if (marked) {
            if (existingMarkedInternship == null) {
                val markedInternship = MarkedInternship(
                    internship = internship,
                    user = user,
                )
                user.markedInternships.add(markedInternship)
                internship.markedInternship.add(markedInternship)

                markedInternshipRepository.save(markedInternship)
            }
        } else {
            if (existingMarkedInternship != null) {
                user.markedInternships.remove(existingMarkedInternship)
                internship.markedInternship.remove(existingMarkedInternship)

                markedInternshipRepository.delete(existingMarkedInternship)
            }
        }
    }

    override fun getBookmarkedInternships(userId: Long): List<InternshipResponseDTO>? {
        val company: Optional<Company> = companyRepository.findById(userId)
        if (company.isEmpty) {
            throw NoSuchElementException("Company with id $userId not found")
        }
        val internships = isInternshipsMark(company.get().profile?.user?.id!!, company.get().internships)
        val bookmarkedInternships = internships.filter { it.bookmarked }

        return if (bookmarkedInternships.isNotEmpty()) {
            bookmarkedInternships.map { internshipMapper.internshipToInternshipResponseDTO(it) }
        } else {
            null // Return null if no bookmarked internships are found
        }
    }

    /**
     * Marks internships as bookmarked for a user.
     * @param userId The ID of the user.
     * @param internships The list of internships to check for bookmarks.
     * @return A list of internships with their bookmarked status updated.
     */
    private fun isInternshipsMark(userId: Long, internships: List<Internship>?): List<Internship> {
        val markedInternships = markedInternshipRepository.findByUserId(userId)

        // If the internships list is not null or empty, update the bookmarked status
        if (!internships.isNullOrEmpty()) {
            internships.forEach { internship ->
                internship.bookmarked = markedInternships.any { it.internship.id == internship.id }
            }
            return internships
        }

        // If internships is null or empty, return an empty list
        return emptyList()
    }

}

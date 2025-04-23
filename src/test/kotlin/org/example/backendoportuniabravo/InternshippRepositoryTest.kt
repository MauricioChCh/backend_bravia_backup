package org.example.backendoportuniabravo

import org.assertj.core.api.Assertions.assertThat
import org.example.backendoportuniabravo.entities.BusinessArea
import org.example.backendoportuniabravo.entities.Internship
import org.example.backendoportuniabravo.repositories.BusinessAreaRepository
import org.example.backendoportuniabravo.repositories.InternshipRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InternshipRepositoryTest {

    @Autowired
    lateinit var internshipRepository: InternshipRepository

    @Autowired
    lateinit var businessAreaRepository: BusinessAreaRepository

    @BeforeEach
    fun setup() {
        // Crear áreas de negocio de prueba
        val techArea = businessAreaRepository.save(BusinessArea(name = "Tecnología"))
        val financeArea = businessAreaRepository.save(BusinessArea(name = "Finanzas"))

        // Crear pasantías de prueba
        val internship1 = Internship(
            title = "Desarrollador Backend",
            company = "Tech Corp",
            companyId = 1,
            location = "Buenos Aires",
            publicationDate = Date(),
            duration = "6 meses",
            modality = "Remoto",
            schedule = "Full-time",
            requirements = "Java, Spring Boot",
            activities = "Desarrollo de APIs",
            contact = "hr@tech.com",
            link = "https://tech.com/internships"
        ).apply { businessAreas.add(techArea) }

        val internship2 = Internship(
            title = "Analista Financiero",
            company = "Finance Inc",
            companyId = 2,
            location = "Córdoba",
            publicationDate = Date(System.currentTimeMillis() - 86400000), // Ayer
            duration = "3 meses",
            modality = "Presencial",
            schedule = "Part-time",
            requirements = "Excel, Contabilidad",
            activities = "Análisis de datos",
            contact = "jobs@finance.com",
            link = "https://finance.com/careers"
        ).apply { businessAreas.add(financeArea) }

        internshipRepository.saveAll(listOf(internship1, internship2))
    }

    @Test
    fun `findByTitleContainingIgnoreCase should return matching internships`() {
        val result = internshipRepository.findByTitleContainingIgnoreCase("desarrollador")
        assertThat(result).hasSize(1)
        assertThat(result[0].title).containsIgnoringCase("desarrollador")
    }

    @Test
    fun `findByLocationIgnoreCase should return internships in specified location`() {
        val result = internshipRepository.findByLocationIgnoreCase("buenos aires")
        assertThat(result).hasSize(1)
        assertThat(result[0].location).isEqualTo("Buenos Aires")
    }

    @Test
    fun `findByModalityIgnoreCase should return remote internships`() {
        val result = internshipRepository.findByModalityIgnoreCase("remoto")
        assertThat(result).hasSize(1)
        assertThat(result[0].modality).isEqualTo("Remoto")
    }

    @Test
    fun `findByBusinessAreasId should return internships in tech area`() {
        val techArea = businessAreaRepository.findByNameIgnoreCase("Tecnología").get()
        val result = internshipRepository.findByBusinessAreasId(techArea.id!!)
        assertThat(result).hasSize(1)
        assertThat(result[0].company).isEqualTo("Tech Corp")
    }

    @Test
    fun `findAllByOrderByPublicationDateDesc should return internships ordered by date`() {
        val result = internshipRepository.findAllByOrderByPublicationDateDesc()
        assertThat(result).hasSize(2)
        assertThat(result[0].title).isEqualTo("Desarrollador Backend") // Más reciente
    }

    @Test
    fun `findRandomInternships should return limited number of internships`() {
        val result = internshipRepository.findRandomInternships(1)
        assertThat(result).hasSize(1)
    }
}
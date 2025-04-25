package org.example.backendoportuniabravo

import org.example.backendoportuniabravo.entities.*
import org.example.backendoportuniabravo.repositories.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class InternshipRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val profileRepository: ProfileRepository,
    val countryRepository: CountryRepository,
    val provinceRepository: ProvinceRepository,
    val locationRepository: LocationRepository,
    val companyRepository: CompanyRepository,
    val internshipRepository: InternshipRepository
) {

    @Test
    fun `crear internships y probar queries`() {
        // Crear entidades base
        val user = userRepository.save(
            User(
                createDate = Date(),
                firstName = "Carlos",
                lastName = "Ramírez",
                email = "carlos@email.com",
                password = "123456",
                tokenExpired = false,
                enabled = true
            )
        )

        val profile = profileRepository.save(
            Profile(userId = user.id!!.toInt(), verified = true)
        )

        val country = countryRepository.save(Country(name = "Costa Rica"))
        val province = provinceRepository.save(Province(name = "San José"))

        val location = locationRepository.save(
            Location(
                profile = profile,
                province = province,
                country = country,
                address = "Calle 15, San Pedro"
            )
        )

        val company = companyRepository.save(
            Company(
                user = user,
                name = "TechSoft",
                description = "Empresa de software",
                location = "San José",
                contact = "contacto@techsoft.com"
            )
        )

        // Crear múltiples pasantías
        val internships = listOf(
            Internship(
                title = "Backend Developer",
                imageUrl = null,
                publicationDate = Date(),
                duration = "6 meses",
                salary = 500.0,
                modality = "Remoto",
                schedule = "Lunes a viernes",
                requirements = "Conocimiento en Spring Boot",
                activities = "Desarrollar APIs REST",
                link = "https://techsoft.com/backend",
                location = location,
                company = company
            ),
            Internship(
                title = "Frontend Developer",
                imageUrl = null,
                publicationDate = Date(),
                duration = "3 meses",
                salary = 400.0,
                modality = "Presencial",
                schedule = "Lunes a viernes",
                requirements = "Conocimiento en Angular",
                activities = "Desarrollar interfaces",
                link = "https://techsoft.com/frontend",
                location = location,
                company = company
            )
        )

        internshipRepository.saveAll(internships)

        // Probar las queries
        val resultsByTitle = internshipRepository.findByTitleContainingIgnoreCase("developer")
        println("Por título: ${resultsByTitle.map { it.title }}")

        val resultsByModality = internshipRepository.findByModalityIgnoreCase("remoto")
        println("Por modalidad: ${resultsByModality.map { it.title }}")

        val resultsByProvince = internshipRepository.findByLocation_Province_NameContainingIgnoreCase("San José")
        println("Por provincia: ${resultsByProvince.map { it.title }}")

        val resultsByDate = internshipRepository.findAllByOrderByPublicationDateDesc()
        println("Por fecha publicación: ${resultsByDate.map { it.title }}")

        val recommended = internshipRepository.findAllRecommended(1)
        println("Recomendadas aleatorias: ${recommended.map { it.title }}")
    }
}

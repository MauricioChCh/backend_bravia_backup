package org.example.backendoportuniabravo

import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional
import java.util.*

//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional //Evita que se guarden los cambios en la base de datos
////@Commit

@SpringBootTest
@Transactional
//@Testcontainers
class InternshipRepositoryTest  @Autowired constructor (
    val userRepository: UserRepository,
    val profileRepository: ProfileRepository,
    val countryRepository: CountryRepository,
    val cityRepository: CityRepository,
    val locationRepository: LocationRepository,
    val companyRepository: CompanyRepository,
    val internshipRepository: InternshipRepository
): BaseIntegrationTest() {


    @Autowired
    private lateinit var contactRepository: ContactRepository

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
            Profile(user = user, verified = true)
        )

        val country = countryRepository.save(Country(name = "Costa Rica"))
        val province = cityRepository.save(City(name = "San José"))

        val location = locationRepository.save(
            Location(
                city = province,
                country = country,
                address = "Calle 15, San Pedro"
            )
        )

        val company = companyRepository.save(
            Company(
                profile = profile,
                name = "TechSoft",
                description = "Empresa de software"
            )
        )

        val contact = Contact(url = "https://carlos.com/contacto", company = company)

        // Guardar el contacto
        contactRepository.save(contact)
        company.contacts.add(contact)
        companyRepository.save(company)

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

        val resultsByProvince = internshipRepository.findByLocation_City_NameContainingIgnoreCase("San José")
        println("Por provincia: ${resultsByProvince.map { it.title }}")

        val resultsByDate = internshipRepository.findAllByOrderByPublicationDateDesc()
        println("Por fecha publicación: ${resultsByDate.map { it.title }}")

        val recommended = internshipRepository.findAllRecommended(1)
        println("Recomendadas aleatorias: ${recommended.map { it.title }}")
    }
}

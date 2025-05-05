package org.example.backendoportuniabravo.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*

@Configuration
class DataSeeder {
    @Bean
    fun insertSampleInternships(
        userRepository: UserRepository,
        profileRepository: ProfileRepository,
        countryRepository: CountryRepository,
        cityRepository: CityRepository,
        locationRepository: LocationRepository,
        companyRepository: CompanyRepository,
        internshipRepository: InternshipRepository
    ): CommandLineRunner {
        return CommandLineRunner {

            // Only insert if the table is empty
            if (internshipRepository.count() == 0L) {
                println("📦 Inserting test data into Docker...")

                val user = userRepository.save(
                    User(
                        createDate = Date(),
                        firstName = "María",
                        lastName = "Gómez",
                        email = "maria@email.com",
                        password = "123456",
                        tokenExpired = false,
                        enabled = true
                    )
                )

                val profile = profileRepository.save(Profile(user = user, verified = true))
                val country = countryRepository.save(Country(name = "Costa Rica"))
                val city = cityRepository.save(City(name = "Heredia"))

                val location = Location(
                        city = city,
                        country = country,
                        address = "Barrio Tournón"
                )


                // Create the company with the managed location
                val company = Company(
                    profile = profile,  // Use profile instead of user
                    name = "DevTechDockeeer",
                    description = "Compañía de desarrollo",
                    location = location  // Pass managed location here
                )

                // Save the company first
                val savedCompany = companyRepository.save(company)

                // Add contact to the saved company
                val contact = Contact(
                    id = null,  // ID will be generated automatically
                    url = "https://devtech.com/contacto",  // Contact URL
                    company = savedCompany
                )

                // Use the contactRepository or company object directly to handle the relationship
                savedCompany.contacts.add(contact)

                // Save company again with its updated contacts
                companyRepository.save(savedCompany)

                // Create internship using the managed location and saved company
                val internship = Internship(
                    title = "Fullstack Developer Docker",
                    imageUrl = null,
                    publicationDate = Date(),
                    duration = "4 meses",
                    salary = 700.0,
                    modality = "Remoto",
                    schedule = "Lunes a viernes",
                    requirements = "Kotlin, Angular",
                    activities = "Desarrollar frontend y backend",
                    link = "https://devtech.com/oportunidad",
                    location = location,  // Use managed location
                    company = savedCompany  // Use saved (managed) company instance
                )

                internshipRepository.save(internship)

                println("✅ Test internship inserted")
            } else {
                println("⚠️ Data already exists. No data was inserted.")
            }
        }
    }
}
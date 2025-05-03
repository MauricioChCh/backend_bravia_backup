package org.example.backendoportuniabravo.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*


@Configuration
class DataSeeder {
    // DataSeeder.kt con correcciones
    @Bean
    fun insertSampleInternships(
        userRepository: UserRepository,
        profileRepository: ProfileRepository,
        countryRepository: CountryRepository,
        provinceRepository: ProvinceRepository,
        locationRepository: LocationRepository,
        companyRepository: CompanyRepository,
        internshipRepository: InternshipRepository
    ): CommandLineRunner {
        return CommandLineRunner {

            // Solo insertar si la tabla está vacía
            if (internshipRepository.count() == 0L) {
                println("📦 Insertando datos de prueba en Docker...")

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
                val province = provinceRepository.save(Province(name = "Heredia"))

                val location = locationRepository.save(
                    Location(
                        profile = profile,
                        province = province,
                        country = country,
                        address = "Barrio Tournón"
                    )
                )

                // Primero creamos la compañía
                val company = companyRepository.save(
                    Company(
                        profile = profile,  // Cambio: usar profile en lugar de user
                        name = "DevTechDockeeer",
                        description = "Compañía de desarrollo",
                        location = location,  // Cambio: usar objeto Location en lugar de String
                        // No asignamos contacts ahora porque necesitamos la compañía ya guardada
                    )
                )

                // Ahora creamos el contacto con la compañía ya guardada
                // No usamos contactRepository directamente para evitar errores de tipo
                val contact = Contact(
                    id = null,  // El ID se generará automáticamente
                    url = "https://devtech.com/contacto",  // URL de contacto
                    company = company
                )

                // Añadimos el contacto directamente a la lista de contactos de la compañía
                company.contacts.add(contact)

                // Guardamos la compañía actualizada con sus contactos
                companyRepository.save(company)

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
                    location = location,
                    company = company
                )

                internshipRepository.save(internship)

                println("✅ Internship de prueba insertado")
            } else {
                println("⚠️ Ya existen datos. No se insertó nada.")
            }
        }
    }
}

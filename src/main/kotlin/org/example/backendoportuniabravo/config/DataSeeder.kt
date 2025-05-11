package org.example.backendoportuniabravo.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*

@Configuration
class DataSeeder {
//    @Bean
//    fun insertSampleInternships(
//        userRepository: UserRepository,
//        profileRepository: ProfileRepository,
//        countryRepository: CountryRepository,
//        cityRepository: CityRepository,
//        locationRepository: LocationRepository,
//        companyRepository: CompanyRepository,
//        internshipRepository: InternshipRepository
//    ): CommandLineRunner {
//        return CommandLineRunner {
//
//            // Only insert if the table is empty
//            if (internshipRepository.count() == 0L) {
//                println("📦 Inserting test data into Docker...")
//
//                val user = User(
//                        createDate = Date(),
//                        firstName = "María",
//                        lastName = "Gómez",
//                        email = "maria@email.com",
//                        password = "123456",
//                        tokenExpired = false,
//                        enabled = true
//                )
//
//                val profile = Profile(user = user, verified = true)
//                user.profile = profile
//                userRepository.save(user)
//
//                val managedProfile = profileRepository.findById(profile.id!!)
//                    .orElseThrow { IllegalStateException("Profile not found!") }
//
//
//
//                val country = countryRepository.save(Country(name = "Costa Rica"))
//                val city = cityRepository.save(City(name = "Heredia"))
//
//                val savedLocation = locationRepository.save(
//                        Location(
//                            city = city,
//                            country = country,
//                            address = "Barrio Tournón"
//                    )
//                )
//
//                // Create the company with the managed location
//                val company = Company(
//                    profile = managedProfile,  // Use profile instead of user
//                    name = "DevTechDockeeer",
//                    description = "Compañía de desarrollo",
//                    location = savedLocation  // Pass managed location here
//                )
//
//                // Save the company first
//                val savedCompany = companyRepository.save(company)
//
//                // Add contact to the saved company
//                val contact = Contact(
//                    url = "https://devtech.com/contacto",  // Contact URL
//                    company = savedCompany
//                )
//
//                // Use the contactRepository or company object directly to handle the relationship
//                savedCompany.contacts.add(contact)
//
//                // Save company again with its updated contacts
//                companyRepository.save(savedCompany)
//
//                // Create internship using the managed location and saved company
//                val internship = Internship(
//                    title = "Fullstack Developer Docker",
//                    imageUrl = null,
//                    publicationDate = Date(),
//                    duration = "4 meses",
//                    salary = 700.0,
//                    modality = "Remoto",
//                    schedule = "Lunes a viernes",
//                    requirements = "Kotlin, Angular",
//                    activities = "Desarrollar frontend y backend",
//                    link = "https://devtech.com/oportunidad",
//                    location = savedLocation,  // Use managed location
//                    company = savedCompany  // Use saved (managed) company instance
//                )
//
//                internshipRepository.save(internship)
//
//                println("✅ Test internship inserted")
//            } else {
//                println("⚠️ Data already exists. No data was inserted.")
//            }
//        }
//    }

    @Bean
    fun insertSampleForCompany(businessAreaRepository: BusinessAreaRepository, tagRepository: TagRepository,
                               countryRepository: CountryRepository, cityRepository: CityRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting test data into Docker...")

            // Only insert if the table is empty
            if (businessAreaRepository.findAll().isEmpty()) {
                businessAreaRepository.save(BusinessArea(name = "Ventas"))
                businessAreaRepository.save(BusinessArea(name = "Desarrollo"))
                businessAreaRepository.save(BusinessArea(name = "Marketing"))
                println("✅ Test business areas inserted")
            } else {
                println("⚠️ Data for bvusiness areas already exists. No data was inserted.")
            }
            if (tagRepository.findAll().isEmpty()){
                tagRepository.save(Tag(name = "Desarrollo"))
                tagRepository.save(Tag(name = "Backend"))
                tagRepository.save(Tag(name = "Frontend"))
                tagRepository.save(Tag(name = "Kotlin"))
                tagRepository.save(Tag(name = "Java"))
                println("✅ Test tags inserted")
            } else {
                println("⚠️ Data for tags already exists. No data was inserted.")
            }

            if(countryRepository.findAll().isEmpty() && cityRepository.findAll().isEmpty()){
                countryRepository.save(Country(name = "Costa Rica"))
                countryRepository.save(Country(name = "Colombia"))
                countryRepository.save(Country(name = "Argentina"))

                cityRepository.save(City(name = "San José"))
                cityRepository.save(City(name = "Cartago"))
                cityRepository.save(City(name = "Heredia"))
                cityRepository.save(City(name = "Bogotá"))
                cityRepository.save(City(name = "Cali"))
                cityRepository.save(City(name = "Buenos Aires"))

                println("✅ Test countries and cities inserted")
            } else {
                println("⚠️ Data for countries and cities already exists. No data was inserted.")
            }

        }
    }
}
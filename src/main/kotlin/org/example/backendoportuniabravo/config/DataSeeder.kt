package org.example.backendoportuniabravo.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*

@Configuration
class DataSeeder {
        @Bean
        fun insertLocations(
            countryRepository: CountryRepository,
            cityRepository: CityRepository,
            locationRepository: LocationRepository
        ): CommandLineRunner {
            return CommandLineRunner {
                println("📦 Inserting Location Data...")

                val country = countryRepository.findByName("Costa Rica")
                    ?: countryRepository.save(Country(name = "Costa Rica"))

                //Verifica si la ciudad ya existe, si no existe la crea por medio e una lista asociativa
                val cities = listOf("Heredia", "San José", "Cartago", "Alajuela", "Limón").associateWith {
                    cityRepository.findByName(it) ?: cityRepository.save(City(name = it))
                }

                // Variable de tipo lista que contiene una lista de tuplas triples,
                // donde cada tupla contiene el nombre de la ciudad, la dirección y la ciudad para la facilidad de busqueda de location y la creacion o modificacion de ciudades por defecto
                val expectedLocations = listOf(
                    Triple("Heredia", "Barrio Tournón", cities["Heredia"]),
                    Triple("San José", "Avenida Central", cities["San José"]),
                    Triple("Cartago", "Ruinas de Cartago", cities["Cartago"]),
                    Triple("Alajuela", "Parque Central", cities["Alajuela"]),
                    Triple("Limón", "Puerto Limón", cities["Limón"])
                )

                //Me traigo toda la lista de ubicaciones, especificamente solo las adress
                val existingAddresses = locationRepository.findAll().map { it.address }

                // Filtro la lista de tuplas para quedarme solo con las adress que no existen en la base de datos y luego por cada una de estas verifica si la ciudad no es nula y la agrega
                expectedLocations.filter { it.second !in existingAddresses }
                    .forEach { (cityName, address, city) ->
                        city?.let {
                            locationRepository.save(Location(city = it, country = country, address = address))
                        }
                    }

                println("✅ Location data ensured")
            }
        }

    @Bean
    fun insertStudentMetadata(
        languageRepository: LanguageRepository,
        degreeRepository: DegreeRepository,
        collegeRepository: CollegeRepository,
        interestRepository: InterestRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting Student Metadata...")

            val defaultLanguages = listOf("Español", "Inglés", "Francés", "Alemán", "Portugués")
            val defaultDegrees = listOf("Bachillerato", "Licenciatura", "Maestría", "Doctorado")
            val defaultColleges = listOf("UCR", "TEC", "UNA", "ULATINA", "UNED")
            val defaultInterests = listOf("Inteligencia Artificial", "Ciberseguridad", "Desarrollo Web", "Videojuegos", "Ciencia de Datos")

            defaultLanguages.forEach { name ->
                if (!languageRepository.existsByName(name)) {
                    languageRepository.save(Language(name = name))
                }
            }

            defaultDegrees.forEach { name ->
                if (!degreeRepository.existsByName(name)) {
                    degreeRepository.save(Degree(name = name))
                }
            }

            defaultColleges.forEach { name ->
                if (!collegeRepository.existsByName(name)) {
                    collegeRepository.save(College(name = name))
                }
            }

            defaultInterests.forEach { name ->
                if (!interestRepository.existsByName(name)) {
                    interestRepository.save(Interest(name = name))
                }
            }

            println("✅ Student metadata inserted")
        }
    }


//    @Bean
//    fun insertLocations(
//        countryRepository: CountryRepository,
//        cityRepository: CityRepository,
//        locationRepository: LocationRepository
//    ): CommandLineRunner {
//        return CommandLineRunner {
////            if (countryRepository.findAll().isEmpty() || !countryRepository.existsByName("Costa Rica")) {
//                println("📦 Inserting Location Data...")
//
//                val country = countryRepository.findByName("Costa Rica") ?: countryRepository.save(Country(name = "Costa Rica"))
//
//                val city1 = cityRepository.findByName("Heredia") ?: cityRepository.save(City(name = "Heredia"))
//                val city2 = cityRepository.findByName("San José") ?: cityRepository.save(City(name = "San José"))
//                val city3 = cityRepository.findByName("Cartago") ?: cityRepository.save(City(name = "Cartago"))
//                val city4 = cityRepository.findByName("Alajuela") ?: cityRepository.save(City(name = "Alajuela"))
//                val city5 = cityRepository.findByName("Limón") ?: cityRepository.save(City(name = "Limón"))
//
//                if (locationRepository.findAll().isEmpty()) {
//                    locationRepository.save(Location(city = city1, country = country, address = "Barrio Tournón"))
//                    locationRepository.save(Location(city = city2, country = country, address = "Avenida Central"))
//                    locationRepository.save(Location(city = city3, country = country, address = "Ruinas de Cartago"))
//                    locationRepository.save(Location(city = city4, country = country, address = "Parque Central"))
//                    locationRepository.save(Location(city = city5, country = country, address = "Puerto Limón"))
//                    println("✅ Test locations inserted")
//                } else {
//                    println("⚠️ Locations already exist. No data was inserted.")
//                }
////            } else {
////                println("⚠️ Country or cities already exist. No data was inserted.")
////            }
//        }
//    }


//    @Bean
//    fun insertSampleInternships(
//        userRepository: UserRepository,
//        countryRepository: CountryRepository,
//        cityRepository: CityRepository,
//        locationRepository: LocationRepository,
//        companyRepository: CompanyRepository,
//        internshipRepository: InternshipRepository
//    ): CommandLineRunner {
//        return CommandLineRunner {
//            if (internshipRepository.count() == 0L) {
//                println("📦 Inserting test data...")
//                // Crear el usuario
//                val user = User(
//                    createDate = Date(),
//                    firstName = "María",
//                    lastName = "Gómez",
//                    email = "maria@email.com",
//                    password = "123456",
//                    tokenExpired = false,
//                    enabled = true
//                ).apply {
//                    addProfile(Profile(verified = true))
//                }
//                val savedUser = userRepository.save(user)
//
//                // 2. Obtener el perfil gestionado
//                val managedProfile = savedUser.profile!!
//                println("✅ User and Profile created")
//
//                // 3. Crear ubicación
//                val country = countryRepository.save(Country(name = "Costa Rica"))
//                val city = cityRepository.save(City(name = "Heredia"))
//                val location = locationRepository.save(
//                    Location(city = city, country = country, address = "Barrio Tournón")
//                )
//                println("✅ Location created")
//
//                // 4. Crear compañía
//                val company = companyRepository.save(
//                    Company(
//                        profile = managedProfile,
//                        name = "DevTech",
//                        description = "Desarrollo de software",
//                        location = location
//                    )
//                )
//                println("✅ Company created")
//
//                // Crear pasantía
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
//                    company = company,
//                    location = location,
//                )
//
//                internshipRepository.save(internship)
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


//
}
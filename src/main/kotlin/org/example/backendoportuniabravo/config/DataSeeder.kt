package org.example.backendoportuniabravo.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Configuration
class DataSeeder {
    @Bean
    @Order(1)
    @Transactional
    fun insertRolesAndPrivileges(roleRepository: RoleRepository, privilegeRepository: PrivilegeRepository): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting roles and privileges with relationships...")

            // Solo ejecuta la inserción si las tablas están vacías
            if (roleRepository.findAll().isEmpty() && privilegeRepository.findAll().isEmpty()) {
                // Crear y guardar los privilegios
                val privilegeRead = privilegeRepository.save(Privilege(name = "READ_PRIVILEGE"))
                val privilegeWrite = privilegeRepository.save(Privilege(name = "WRITE_PRIVILEGE"))
                val privilegeDelete = privilegeRepository.save(Privilege(name = "DELETE_PRIVILEGE"))
                val privilegeUpdate = privilegeRepository.save(Privilege(name = "UPDATE_PRIVILEGE"))

                // Crear y guardar los roles con sus privilegios relacionados
                val roleAdmin = Role(name = "ROLE_ADMIN").apply {
                    privilegeList = mutableSetOf(privilegeRead, privilegeWrite, privilegeDelete, privilegeUpdate)
                }
                val roleStudent = Role(name = "ROLE_STUDENT").apply {
                    privilegeList = mutableSetOf(privilegeRead, privilegeUpdate)
                }
                val roleCompany = Role(name = "ROLE_COMPANY").apply {
                    privilegeList = mutableSetOf(privilegeRead, privilegeWrite, privilegeUpdate)
                }

                roleRepository.saveAll(listOf(roleAdmin, roleStudent, roleCompany))
                println("✅ Roles and privileges inserted successfully")
            } else {
                println("⚠️ Roles and privileges already exist. No data was inserted.")
            }
        }
    }

        @Bean
        @Order(2)
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
    @Order(3)
    fun insertStudentMetadata(
        languageRepository: LanguageRepository,
        degreeRepository: DegreeRepository,
        collegeRepository: CollegeRepository,
        interestRepository: InterestRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting Student Metadata...")

            val defaultLanguages = listOf("Español", "Inglés", "Francés", "Alemán", "Portugués")
            val defaultDegrees = listOf("Diplomado","Bachillerato", "Licenciatura", "Maestría", "Doctorado")
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


    @Bean
    @Order(4)
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



    @Bean
    @Order(5)
    fun insertSampleAdmins(
        userRepository: UserRepository,
        profileRepository: ProfileRepository,
        adminRepository: AdminRepository,
        roleRepository: RoleRepository, passwordEncoder: BCryptPasswordEncoder,
    ): CommandLineRunner = CommandLineRunner {

        if (adminRepository.count() == 0L) {
            println("📦 Inserting admin test data…")

            val profile = Profile(verified = true)
            val profile2 = Profile(verified = true)

            val user = User(
                createDate = Date(),
                firstName = "Gabriel",
                lastName = "Vega",
                email = "admin1.admin1@example.com",
                password = passwordEncoder.encode("Password123!"),
                tokenExpired = false,
                enabled = true,
                profile = profile,
                roleList = mutableSetOf(
                    roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow { NoSuchElementException("Role not found") }
                )

            )

            val user2 = User(
                createDate = Date(),
                firstName = "Mauricio",
                lastName = "Chaves",
                email = "admin2.admin2@example.com",
                password = passwordEncoder.encode("Password123!"),
                tokenExpired = false,
                enabled = true,
                profile = profile2,
                roleList = mutableSetOf(
                    roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow { NoSuchElementException("Role not found") }
                )

            )

            val admin = Admin(profile = profile)
            val admin2 = Admin(profile = profile2)

            profile.user = user
            profile.admin = admin

            profile2.user = user2
            profile2.admin = admin2

            userRepository.save(user)

            userRepository.save(user2)

            println("✅ Admin test data inserted")
        } else {
            println("⚠️ Admin data already exists. No data was inserted.")
        }
    }


    @Bean
    @Order(6)
    fun insertUserReportsData(
        userRepository: UserRepository,
        profileRepository: ProfileRepository,
        roleRepository: RoleRepository,
        userReportRepository: UserReportRepository,
        passwordEncoder: BCryptPasswordEncoder
    ): CommandLineRunner = CommandLineRunner {
        if (userReportRepository.count() == 0L) {
            println("📦 Inserting user reports test data…")

            val profileReporter = Profile(verified = true)
            val profileReported = Profile(verified = true)

            val roleStudent = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow { NoSuchElementException("Role ROLE_STUDENT not found") }

            val reporter = User(
                createDate = Date(),
                firstName = "Luis",
                lastName = "Cordero",
                email = "reporter@example.com",
                password = passwordEncoder.encode("Password123!"),
                tokenExpired = false,
                enabled = true,
                profile = profileReporter,
                roleList = mutableSetOf(roleStudent)
            )

            val reported = User(
                createDate = Date(),
                firstName = "Carlos",
                lastName = "Ramírez",
                email = "reported@example.com",
                password = passwordEncoder.encode("Password123!"),
                tokenExpired = false,
                enabled = true,
                profile = profileReported,
                roleList = mutableSetOf(roleStudent)
            )

            profileReporter.user = reporter
            profileReported.user = reported

            userRepository.saveAll(listOf(reporter, reported))

            val report = UserReports(
                user = reporter,
                userReported = reported,
                description = "Este usuario compartió información falsa en su perfil."
            )

            userReportRepository.save(report)

            println("✅ User report test data inserted")
        } else {
            println("⚠️ User report data already exists. No data was inserted.")
        }
    }


}
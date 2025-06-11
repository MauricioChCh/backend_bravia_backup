package org.example.backendoportuniabravo.config

import org.example.backendoportuniabravo.dto.*
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
    @Order(1) // Ejecutar PRIMERO
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
        @Transactional
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
    @Order(3) // Ejecutar tercero
    @Transactional
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
    @Order(4) // Ejecutar CUARTO
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
    @Order(5) // Ejecutar QUINTO
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
    @Order(6) // Ejecutar SEXTO
    @Transactional
    fun insertSampleStudents(
        userRepository: UserRepository,
        profileRepository: ProfileRepository,
        studentRepository: StudentRepository,
        roleRepository: RoleRepository,
        languageRepository: LanguageRepository,
        degreeRepository: DegreeRepository,
        collegeRepository: CollegeRepository,
        interestRepository: InterestRepository,
        passwordEncoder: BCryptPasswordEncoder
    ): CommandLineRunner = CommandLineRunner {

        if (studentRepository.count() == 0L) {
            println("📦 Inserting student test data...")

            // Obtener el rol de estudiante
            val studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow { NoSuchElementException("ROLE_STUDENT not found") }

            // Lista de estudiantes de ejemplo con IDs/nombres en lugar de entidades
            val studentsData = listOf(


                StudentSeedData(
                    firstName = "Prueba",
                    lastName = "Pruebon",
                    email = "prueba@gmail.com",
                    description = "Desarrollador full-stack en formación con gran interés en tecnologías emergentes. Me gusta crear soluciones web modernas y estoy constantemente aprendiendo nuevas tecnologías.",
                    academicCenter = "Universidad Nacional - Campus Benjamin nuñez",
                    hobbies = listOf("Desarrollo web", "Gaming", "Deportes"),
                    certifications = listOf(
                        CertificationSeedData("Full Stack Web Development", "2023-09-30", "freeCodeCamp"),
                        CertificationSeedData("MongoDB Developer", "2023-12-01", "MongoDB University")
                    ),
                    experiences = listOf(
                        ExperienceSeedData("Desarrollador Freelance", "Desarrollo de sitios web para pequeñas empresas usando tecnologías modernas."),
                        ExperienceSeedData("Interno de Desarrollo", "Colaboración en el desarrollo de aplicaciones móviles para startup local.")
                    ),
                    skills = listOf(
                        SkillSeedData("JavaScript", "Desarrollo frontend y backend con frameworks modernos."),
                        SkillSeedData("Node.js", "Creación de APIs RESTful y aplicaciones del lado del servidor."),
                        SkillSeedData("MongoDB", "Diseño y gestión de bases de datos NoSQL.")
                    ),
                    careers = listOf("Ingeniería de Software"),
                    cvUrls = listOf("https://portfolio-diego.com", "https://github.com/diego-dev"),
                    languageNames = listOf("Español", "Inglés"),
                    degreeNames = listOf("Bachillerato"),
                    collegeNames = listOf("UNA"),
                    interestNames = listOf("Desarrollo Web", "Videojuegos")
                )
            )

            // Crear estudiantes
            studentsData.forEach { studentData ->
                try {
                    // 1. Crear y guardar el usuario primero (sin relaciones complejas)
                    val profile = Profile(verified = true)
                    val user = User(
                        createDate = Date(),
                        firstName = studentData.firstName,
                        lastName = studentData.lastName,
                        email = studentData.email,
                        password = passwordEncoder.encode("password!"),
                        tokenExpired = false,
                        enabled = true,
                        profile = profile,
                        roleList = mutableSetOf(studentRole)
                    )

                    // Crear student básico (sin relaciones ManyToMany)
                    val student = Student(
                        profile = profile,
                        description = studentData.description,
                        academicCenter = studentData.academicCenter
                        // NO incluir languages, degrees, colleges, interests aquí
                    )

                    profile.user = user
                    profile.student = student

                    // 2. Guardar primero (esto persiste user, profile y student)
                    val savedUser = userRepository.save(user)
                    val savedStudent = savedUser.profile?.student!!

                    // 3. AHORA obtener y asignar las relaciones ManyToMany
                    val languages = studentData.languageNames.mapNotNull { name ->
                        languageRepository.findByName(name)
                    }.toMutableSet()

                    val degrees = studentData.degreeNames.mapNotNull { name ->
                        degreeRepository.findByName(name)
                    }.toMutableSet()

                    val colleges = studentData.collegeNames.mapNotNull { name ->
                        collegeRepository.findByName(name)
                    }.toMutableSet()

                    val interests = studentData.interestNames.mapNotNull { name ->
                        interestRepository.findByName(name)
                    }.toMutableSet()

                    // Asignar las relaciones al student ya persistido
                    savedStudent.languages = languages
                    savedStudent.degrees = degrees
                    savedStudent.colleges = colleges
                    savedStudent.interests = interests

                    // 4. Agregar las entidades dependientes (OneToMany)
                    studentData.hobbies.forEach { hobbyName ->
                        val hobby = Hobby(student = savedStudent, name = hobbyName)
                        savedStudent.hobbies?.add(hobby)
                    }

                    studentData.certifications.forEach { certData ->
                        val certification = Certification(
                            student = savedStudent,
                            name = certData.name,
                            date = java.sql.Date.valueOf(certData.date),
                            organization = certData.organization
                        )
                        savedStudent.certifications?.add(certification)
                    }

                    studentData.experiences.forEach { expData ->
                        val experience = Experience(
                            student = savedStudent,
                            name = expData.name,
                            description = expData.description
                        )
                        savedStudent.experiences?.add(experience)
                    }

                    studentData.skills.forEach { skillData ->
                        val skill = Skill(
                            student = savedStudent,
                            name = skillData.name,
                            description = skillData.description
                        )
                        savedStudent.skills?.add(skill)
                    }

                    studentData.careers.forEach { careerName ->
                        val career = Career(student = savedStudent, career = careerName)
                        savedStudent.careers?.add(career)
                    }

                    studentData.cvUrls.forEach { url ->
                        val cvUrl = CVUrl(student = savedStudent, url = url)
                        savedStudent.cvUrls?.add(cvUrl)
                    }

                    // 5. Guardar una vez más para persistir todas las relaciones
                    studentRepository.save(savedStudent)

                    println("✅ Student created: ${studentData.firstName} ${studentData.lastName}")

                } catch (e: Exception) {
                    println("❌ Error creating student ${studentData.firstName} ${studentData.lastName}: ${e.message}")
                    e.printStackTrace()
                }
            }

            println("✅ Student test data insertion completed")
        } else {
            println("⚠️ Student data already exists. No data was inserted.")
        }
    }


    @Bean
    fun insertColleges(collegeRepository: CollegeRepository): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting colleges...")

            // Solo inserta si la tabla está vacía
            if (collegeRepository.findAll().isEmpty()) {
                val colleges = listOf(
                    College(name = "Universidad de Costa Rica"),
                    College(name = "Instituto Tecnológico de Costa Rica"),
                    College(name = "Universidad Nacional"),
                    College(name = "Universidad Latina"),
                    College(name = "Universidad Estatal a Distancia")
                )
                collegeRepository.saveAll(colleges)
                println("✅ Colleges inserted successfully")
            } else {
                println("⚠️ Colleges already exist. No data was inserted.")
            }
        }
    }

    @Bean
    fun insertInterests(interestRepository: InterestRepository): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting interests...")

            // Solo inserta si la tabla está vacía
            if (interestRepository.findAll().isEmpty()) {
                val interests = listOf(
                    Interest(name = "Inteligencia Artificial"),
                    Interest(name = "Ciberseguridad"),
                    Interest(name = "Desarrollo Web"),
                    Interest(name = "Videojuegos"),
                    Interest(name = "Ciencia de Datos")
                )
                interestRepository.saveAll(interests)
                println("✅ Interests inserted successfully")
            } else {
                println("⚠️ Interests already exist. No data was inserted.")
            }
        }
    }

    @Bean
    fun insertDegrees(degreeRepository: DegreeRepository): CommandLineRunner {
        return CommandLineRunner {
            println("📦 Inserting degrees...")

            // Solo inserta si la tabla está vacía
            if (degreeRepository.findAll().isEmpty()) {
                val degrees = listOf(
                    Degree(name = "Diplomado"),
                    Degree(name = "Bachillerato"),
                    Degree(name = "Licenciatura"),
                    Degree(name = "Maestría"),
                    Degree(name = "Doctorado")
                )
                degreeRepository.saveAll(degrees)
                println("✅ Degrees inserted successfully")
            } else {
                println("⚠️ Degrees already exist. No data was inserted.")
            }
        }
    }
}


//                StudentSeedData(
//                    firstName = "Ana",
//                    lastName = "González",
//                    email = "ana.gonzalez@student.com",
//                    description = "Estudiante apasionada por la inteligencia artificial y el machine learning. Busco oportunidades para aplicar mis conocimientos en proyectos reales y contribuir al desarrollo de soluciones innovadoras.",
//                    academicCenter = "Tecnológico de Costa Rica - Campus Central",
//                    hobbies = listOf("Programación", "Lectura técnica", "Fotografía"),
//                    certifications = listOf(
//                        CertificationSeedData("Python for Data Science", "2024-01-15", "Coursera"),
//                        CertificationSeedData("AWS Cloud Practitioner", "2023-11-20", "Amazon Web Services")
//                    ),
//                    experiences = listOf(
//                        ExperienceSeedData("Desarrolladora Junior", "Desarrollo de aplicaciones web usando React y Node.js para proyectos académicos y freelance."),
//                        ExperienceSeedData("Tutora de Programación", "Apoyo a estudiantes de primer año en cursos de programación básica.")
//                    ),
//                    skills = listOf(
//                        SkillSeedData("Python", "Programación avanzada en Python para ciencia de datos y desarrollo web."),
//                        SkillSeedData("React", "Desarrollo de interfaces de usuario modernas y responsivas."),
//                        SkillSeedData("SQL", "Diseño y consulta de bases de datos relacionales.")
//                    ),
//                    careers = listOf("Ingeniería en Computación"),
//                    cvUrls = listOf("https://drive.google.com/ana-cv", "https://linkedin.com/in/ana-gonzalez"),
//                    languageNames = listOf("Español", "Inglés"),
//                    degreeNames = listOf("Bachillerato"),
//                    collegeNames = listOf("TEC"),
//                    interestNames = listOf("Inteligencia Artificial", "Desarrollo Web")
//                ),
//
//                StudentSeedData(
//                    firstName = "Carlos",
//                    lastName = "Rodríguez",
//                    email = "carlos.rodriguez@student.com",
//                    description = "Estudiante de ingeniería enfocado en ciberseguridad y desarrollo de sistemas seguros. Me interesa mucho la protección de datos y la implementación de mejores prácticas de seguridad.",
//                    academicCenter = "Universidad de Costa Rica - Sede Central",
//                    hobbies = listOf("Hacking ético", "Videojuegos", "Música"),
//                    certifications = listOf(
//                        CertificationSeedData("Certified Ethical Hacker", "2023-12-10", "EC-Council"),
//                        CertificationSeedData("CompTIA Security+", "2023-08-05", "CompTIA")
//                    ),
//                    experiences = listOf(
//                        ExperienceSeedData("Asistente de TI", "Soporte técnico y mantenimiento de sistemas en empresa local."),
//                        ExperienceSeedData("Pentester Junior", "Pruebas de penetración en aplicaciones web para proyectos académicos.")
//                    ),
//                    skills = listOf(
//                        SkillSeedData("Kali Linux", "Uso avanzado de herramientas de pentesting y auditoría de seguridad."),
//                        SkillSeedData("Java", "Desarrollo de aplicaciones empresariales seguras."),
//                        SkillSeedData("Network Security", "Configuración y análisis de seguridad en redes.")
//                    ),
//                    careers = listOf("Ingeniería en Sistemas"),
//                    cvUrls = listOf("https://drive.google.com/carlos-cv"),
//                    languageNames = listOf("Español", "Inglés", "Francés"),
//                    degreeNames = listOf("Bachillerato"),
//                    collegeNames = listOf("UCR"),
//                    interestNames = listOf("Ciberseguridad", "Videojuegos")
//                ),
//
//                StudentSeedData(
//                    firstName = "María",
//                    lastName = "Jiménez",
//                    email = "maria.jimenez@student.com",
//                    description = "Estudiante de último año con experiencia en análisis de datos y visualización. Me apasiona convertir datos complejos en insights accionables para tomar mejores decisiones de negocio.",
//                    academicCenter = "Universidad Nacional - Campus Omar Dengo",
//                    hobbies = listOf("Análisis de datos", "Running", "Cocina"),
//                    certifications = listOf(
//                        CertificationSeedData("Google Data Analytics Certificate", "2024-02-20", "Google"),
//                        CertificationSeedData("Tableau Desktop Specialist", "2023-10-15", "Tableau")
//                    ),
//                    experiences = listOf(
//                        ExperienceSeedData("Analista de Datos Practicante", "Análisis de métricas de ventas y creación de dashboards para toma de decisiones."),
//                        ExperienceSeedData("Asistente de Investigación", "Procesamiento y análisis estadístico de datos para proyectos de investigación universitaria.")
//                    ),
//                    skills = listOf(
//                        SkillSeedData("R", "Análisis estadístico y visualización de datos."),
//                        SkillSeedData("Tableau", "Creación de dashboards interactivos y reportes ejecutivos."),
//                        SkillSeedData("Excel Avanzado", "Análisis de datos, macros y funciones estadísticas avanzadas.")
//                    ),
//                    careers = listOf("Estadística", "Administración de Empresas"),
//                    cvUrls = listOf("https://drive.google.com/maria-cv", "https://github.com/maria-data"),
//                    languageNames = listOf("Español", "Inglés"),
//                    degreeNames = listOf("Bachillerato", "Diplomado"),
//                    collegeNames = listOf("UNA"),
//                    interestNames = listOf("Ciencia de Datos", "Inteligencia Artificial")
//                ),

    @Bean
    @Order(7)
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



package org.example.backendoportuniabravo

import jakarta.transaction.Transactional
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
@Transactional
class StudentRepositoryTest(
    @Autowired val studentRepository: StudentRepository,
    @Autowired val hobbieRepository: HobbieRepository,
    @Autowired val certificationRepository: CertificationRepository,
    @Autowired val experienceRepository: ExperienceRepository,
    @Autowired val skillRepository: SkillRepository,
    @Autowired val careerRepository: CareerRepository,
    @Autowired val cvUrlRepository: CVUrlRepository,
    @Autowired val mockInterviewRepository: MockInterviewRepository,
    @Autowired val degreeRepository: DegreeRepository,
    @Autowired val languageRepository: LanguageRepository,
    @Autowired val collegeRepository: CollegeRepository,
    @Autowired val interestRepository: InterestRepository,
    @Autowired val profileRepository: ProfileRepository,
    @Autowired val userRepository: UserRepository,
) : BaseIntegrationTest() {

    lateinit var user: User
    lateinit var profile: Profile
    lateinit var student: Student

    @Test
    fun testCreateStudent() {
         user = User(
                createDate = Date(),
                firstName = "Carlos",
                lastName = "Ramírez",
                email = "carlos@email.com",
                password = "123456",
                tokenExpired = false,
                enabled = true
         )

        profile = Profile(
            user = user,
            verified = true,
        )

        student = Student(
            profile = profile,
            description = "Student of Computer Science",
            academicCenter = "Computer Science Faculty",
        )

        profile.student = student
        user.profile = profile

        student.hobbies.add(Hobbie(name = "Reading", student = student))
        student.certifications.add(Certification(student = student, name = "Java Certification", date = Date(), organization = "Oracle"))
        student.experiences.add(Experience(student = student, name = "Java Experience", description = "Experience of Computer Science"))
        student.skills.add(Skill(student = student, name = "Java", description = "Skill of Computer Science"))
        student.careers.add(Career(student = student, carrer = "Computer Science"))
        student.cvUrls.add(CVUrl(student = student, url = "https://example.com/cv"))
        student.mockInterviews.add(MockInterview(student = student, date = Date(), result = "Pass", transcription = "Mock interview transcription"))


        val degree = Degree(name = "Bachelor's Degree")
        degree.students.add(student)
        student.degrees.add(degree)

        val language = Language(name = "English")
        language.students.add(student)
        student.languages.add(language)

        val college = College(name = "National University")
        college.students.add(student)
        student.colleges.add(college)

        val interest = Interest(name = "Artificial Intelligence")
        interest.students.add(student)
        student.interests.add(interest)


        userRepository.save(user)

        // Verify that the student is saved
        val savedStudent = studentRepository.findById(student.id!!)
        assertTrue(savedStudent.isPresent, "Student should be saved")

        // Verify that the related entities are saved
        val savedHobbies = hobbieRepository.findByStudent(student)
        assertTrue(savedHobbies.isNotEmpty(), "Hobbies should be saved")

        val savedCertifications = certificationRepository.findByStudent(student)
        assertTrue(savedCertifications.isNotEmpty(), "Certifications should be saved")

        val savedExperiences = experienceRepository.findByStudent(student)
        assertTrue(savedExperiences.isNotEmpty(), "Experiences should be saved")

        val savedSkills = skillRepository.findByStudent(student)
        assertTrue(savedSkills.isNotEmpty(), "Skills should be saved")

        val savedDegrees = degreeRepository.findByStudentsContaining(mutableSetOf(student))
        assertTrue(savedDegrees?.isNotEmpty() == true, "Degrees should be saved")

        val savedLanguages = languageRepository.findByStudentsContaining(mutableSetOf(student))
        assertTrue(savedLanguages.isNotEmpty(), "Languages should be saved")

        val savedColleges = collegeRepository.findByStudentsContaining(student)
        assertTrue(savedColleges?.isNotEmpty() == true, "Colleges should be saved")

        val savedInterests = interestRepository.findByStudentsContaining(mutableSetOf(student))
        assertTrue(savedInterests?.isNotEmpty() == true, "Interests should be saved")

        val savedCareers = careerRepository.findByStudent(student)
        assertTrue(savedCareers.isNotEmpty(), "Careers should be saved")

        val savedCVUrls = cvUrlRepository.findByStudent(student)
        assertTrue(savedCVUrls.isNotEmpty(), "CV URLs should be saved")

        val savedMockInterviews = mockInterviewRepository.findByStudent(student)
        assertTrue(savedMockInterviews.isNotEmpty(), "Mock interviews should be saved")

        val savedProfile = profileRepository.findById(profile.id!!)
        assertTrue(savedProfile.isPresent, "Profile should be saved")
    }

    @Test
    fun testReadStudentAndRelationships() {
        // Primero creamos al estudiante y sus relaciones
        testCreateStudent()

        // Buscar el estudiante
        val fetchedStudent = studentRepository.findById(student.id!!).orElse(null)
        assertNotNull(fetchedStudent)

        // Validar datos básicos
        assertEquals("Student of Computer Science", fetchedStudent.description)
        assertEquals("Computer Science Faculty", fetchedStudent.academicCenter)

        // Validar relaciones @OneToMany
        assertTrue(fetchedStudent.hobbies.any { it.name == "Reading" })
        assertTrue(fetchedStudent.certifications.any { it.name == "Java Certification" })
        assertTrue(fetchedStudent.experiences.any { it.name == "Java Experience" })
        assertTrue(fetchedStudent.skills.any { it.name == "Java" })
        assertTrue(fetchedStudent.careers.any { it.carrer == "Computer Science" })
        assertTrue(fetchedStudent.cvUrls.any { it.url == "https://example.com/cv" })
        assertTrue(fetchedStudent.mockInterviews.any { it.result == "Pass" })

        // Validar relaciones @ManyToMany desde el lado de Student
        assertTrue(fetchedStudent.degrees.any { it.name == "Bachelor's Degree" })
        assertTrue(fetchedStudent.languages.any { it.name == "English" })
        assertTrue(fetchedStudent.colleges.any { it.name == "National University" })
        assertTrue(fetchedStudent.interests.any { it.name == "Artificial Intelligence" })

        // Validar relaciones inversas @ManyToMany desde Degree, etc.
        val degree = degreeRepository.findAll().first { it.name == "Bachelor's Degree" }
        assertTrue(degree.students.any { it.id == fetchedStudent.id })

        val language = languageRepository.findAll().first { it.name == "English" }
        assertTrue(language.students.any { it.id == fetchedStudent.id })

        val college = collegeRepository.findAll().first { it.name == "National University" }
        assertTrue(college.students.any { it.id == fetchedStudent.id })

        val interest = interestRepository.findAll().first { it.name == "Artificial Intelligence" }
        assertTrue(interest.students.any { it.id == fetchedStudent.id })
    }



    @Test
    fun testUpdateStudentAndRelationships() {
        // Crear el estudiante y sus relaciones
        testCreateStudent()

        // Obtener el estudiante
        val studentToUpdate = studentRepository.findById(student.id!!).orElse(null)
        assertNotNull(studentToUpdate)

        // Actualizar campos básicos
        studentToUpdate.description = "Updated Description"
        studentToUpdate.academicCenter = "Updated Academic Center"

        // Agregar una nueva skill
        val newSkill = Skill(student = studentToUpdate, name = "Kotlin", description = "Kotlin Developer")
        studentToUpdate.skills.add(newSkill)

        // Agregar nuevo degree
        val newDegree = Degree(name = "Master's Degree")
        newDegree.students.add(studentToUpdate)
        studentToUpdate.degrees.add(newDegree)

        // Eliminar un hobbie
        val hobbieToRemove = studentToUpdate.hobbies.firstOrNull()
        if (hobbieToRemove != null) {
            studentToUpdate.hobbies.remove(hobbieToRemove)
        }

        // Guardar cambios
        studentRepository.save(studentToUpdate)

        // Obtener de nuevo el estudiante para validar
        val updatedStudent = studentRepository.findById(student.id!!).orElse(null)
        assertNotNull(updatedStudent)

        // Verificar cambios
        assertEquals("Updated Description", updatedStudent.description)
        assertEquals("Updated Academic Center", updatedStudent.academicCenter)

        assertTrue(updatedStudent.skills.any { it.name == "Kotlin" })
        assertTrue(updatedStudent.degrees.any { it.name == "Master's Degree" })

        // Verificar que se eliminó el hobbie anterior (si existía)
        if (hobbieToRemove != null) {
            assertFalse(updatedStudent.hobbies.any { it.id == hobbieToRemove.id })
        }
    }



    @Test
    fun testDeleteStudentAndRelationships() {

        testCreateStudent()

        // Delete the student
        userRepository.delete(user)

        // Verify that the student is deleted
        val deletedStudent = studentRepository.findById(student.id!!)
        assertFalse(deletedStudent.isPresent)

        // Verify that related entities are also deleted
        val hobbies = hobbieRepository.findAll()
        assertFalse(hobbies.any { it.student.id == student.id })

        val certifications = certificationRepository.findAll()
        assertFalse(certifications.any { it.student.id == student.id })

        val experiences = experienceRepository.findAll()
        assertFalse(experiences.any { it.student.id == student.id })

        val skills = skillRepository.findAll()
        assertFalse(skills.any { it.student.id == student.id })

        val degrees = degreeRepository.findAll()
        assertFalse(degrees.any { it.students.contains(student) })

        val languages = languageRepository.findAll()
        assertFalse(languages.any { it.students.contains(student) })

        val colleges = collegeRepository.findAll()
        assertFalse(colleges.any { it.students.contains(student) })

        val interests = interestRepository.findAll()
        assertFalse(interests.any { it.students.contains(student) })
    }
}


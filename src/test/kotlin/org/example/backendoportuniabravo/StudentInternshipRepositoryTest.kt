package org.example.backendoportuniabravo

import jakarta.transaction.Transactional
import org.example.backendoportuniabravo.entities.*
import org.example.backendoportuniabravo.repositories.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.Test

@SpringBootTest
@Transactional
class StudentInternshipRepositoryTest(
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
) {

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
        student.cvUrls.add(CVUrl(student = student, url = "http://example.com/cv"))
        student.mockInterviews.add(MockInterview(student = student, date = Date(), result = "Pass", transcription = "Mock interview transcription"))

        student.degrees.add(degreeRepository.save(Degree(name = "Bachelor's Degree")))
        student.languages.add(languageRepository.save(Language(name = "English")))
        student.colleges.add(collegeRepository.save(College(name = "National University")))
        student.interests.add(interestRepository.save(Interest(name = "Artificial Intelligence")))

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
        val savedDegrees = degreeRepository.findByStudentContaining(mutableSetOf(student))
        assertTrue(savedDegrees?.isNotEmpty() == true, "Degrees should be saved")
        val savedLanguages = languageRepository.findByStudentContaining(mutableSetOf(student))
        assertTrue(savedLanguages.isNotEmpty(), "Languages should be saved")
        val savedColleges = collegeRepository.findByStudentContaining(student)
        assertTrue(savedColleges?.isNotEmpty() == true, "Colleges should be saved")
        val savedInterests = interestRepository.findByStudentContaining(mutableSetOf(student))
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

//    @Test
//    fun testReadStudentAndRelationships() {
//
//        testCreateStudent()
//
//        // Fetch the student from the repository
//        val fetchedStudent = studentRepository.findById(student.id!!).orElse(null)
//        assertNotNull(fetchedStudent)
//
//        // Verify the student's basic details
//        assertEquals("Student of Computer Science", fetchedStudent.description)
//        assertEquals("Computer Science Faculty", fetchedStudent.academicCenter)
//
//        // Verify related entities
//        val hobbies = hobbieRepository.findAll()
//        assertTrue(hobbies.any { it.name == "Reading" && it.student.id == fetchedStudent.id })
//
//        val certifications = certificationRepository.findAll()
//        assertTrue(certifications.any { it.name == "Java Certification" && it.student.id == fetchedStudent.id })
//
//        val experiences = experienceRepository.findAll()
//        assertTrue(experiences.any { it.name == "Java Experience" && it.student.id == fetchedStudent.id })
//
//        val skills = skillRepository.findAll()
//        assertTrue(skills.any { it.name == "Java" && it.student.id == fetchedStudent.id })
//
//        val degrees = degreeRepository.findAll()
//        assertTrue(degrees.any { it.name == "Bachelor's Degree" && it.students.contains(fetchedStudent) })
//
//        val languages = languageRepository.findAll()
//        assertTrue(languages.any { it.name == "English" && it.students.contains(fetchedStudent) })
//
//        val colleges = collegeRepository.findAll()
//        assertTrue(colleges.any { it.name == "National University" && it.students.contains(fetchedStudent) })
//
//        val interests = interestRepository.findAll()
//        assertTrue(interests.any { it.name == "Artificial Intelligence" && it.students.contains(fetchedStudent) })
//
//
//    }

//    @Test
//    fun testUpdateStudentAndRelationships() {
//
//        testCreateStudent()
//
//        // Update the student's basic details
//        student.description = "Updated description"
//        student.academicCenter = "Updated Faculty"
//
//        // Update related entities
//        val hobbie = hobbieRepository.findAll().find { it.student.id == student.id }
//        assertNotNull(hobbie, "Hobbie not found")
//        hobbie!!.name = "Updated Hobby"
//        hobbieRepository.save(hobbie)
//
//        val certification = certificationRepository.findAll().find { it.student.id == student.id }
//        assertNotNull(certification, "Certification not found")
//        certification!!.name = "Updated Certification"
//        certificationRepository.save(certification)
//
//        val experience = experienceRepository.findAll().find { it.student.id == student.id }
//        assertNotNull(experience, "Experience not found")
//        experience!!.name = "Updated Experience"
//        experienceRepository.save(experience)
//
//        val skill = skillRepository.findAll().find { it.student.id == student.id }
//        assertNotNull(skill, "Skill not found")
//        skill!!.name = "Updated Skill"
//        skillRepository.save(skill)
//
//        val degree = degreeRepository.findAll().find { it.students.contains(student) }
//        assertNotNull(degree, "Degree not found")
//        degree!!.name = "Updated Degree"
//        degreeRepository.save(degree)
//
//        val language = languageRepository.findAll().find { it.students.contains(student) }
//        assertNotNull(language, "Language not found")
//        language!!.name = "Updated Language"
//        languageRepository.save(language)
//
//        val college = collegeRepository.findAll().find { it.students.contains(student) }
//        assertNotNull(college, "College not found")
//        college!!.name = "Updated College"
//        collegeRepository.save(college)
//
//        val interest = interestRepository.findAll().find { it.students.contains(student) }
//        assertNotNull(interest, "Interest not found")
//        interest!!.name = "Updated Interest"
//        interestRepository.save(interest)
//
//        // Save the updated student
//        studentRepository.save(student)
//
//        // Fetch the updated student and verify changes
//        val updatedStudent = studentRepository.findById(student.id!!).orElse(null)
//        assertNotNull(updatedStudent)
//        assertEquals("Updated description", updatedStudent.description)
//        assertEquals("Updated Faculty", updatedStudent.academicCenter)
//
//        // Verify updated related entities
//        val updatedHobbie = hobbieRepository.findById(hobbie.id!!).orElse(null)
//        assertNotNull(updatedHobbie)
//        assertEquals("Updated Hobby", updatedHobbie.name)
//
//        val updatedCertification = certificationRepository.findById(certification.id!!).orElse(null)
//        assertNotNull(updatedCertification)
//        assertEquals("Updated Certification", updatedCertification.name)
//
//        val updatedExperience = experienceRepository.findById(experience.id!!).orElse(null)
//        assertNotNull(updatedExperience)
//        assertEquals("Updated Experience", updatedExperience.name)
//
//        val updatedSkill = skillRepository.findById(skill.id!!).orElse(null)
//        assertNotNull(updatedSkill)
//        assertEquals("Updated Skill", updatedSkill.name)
//
//        val updatedDegree = degreeRepository.findById(degree.id!!).orElse(null)
//        assertNotNull(updatedDegree)
//        assertEquals("Updated Degree", updatedDegree.name)
//
//        val updatedLanguage = languageRepository.findById(language.id!!).orElse(null)
//        assertNotNull(updatedLanguage)
//        assertEquals("Updated Language", updatedLanguage.name)
//
//        val updatedCollege = collegeRepository.findById(college.id!!).orElse(null)
//        assertNotNull(updatedCollege)
//        assertEquals("Updated College", updatedCollege.name)
//
//        val updatedInterest = interestRepository.findById(interest.id!!).orElse(null)
//        assertNotNull(updatedInterest)
//        assertEquals("Updated Interest", updatedInterest.name)
//    }

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
        assertFalse(degrees.any { it.student.contains(student) })

        val languages = languageRepository.findAll()
        assertFalse(languages.any { it.student.contains(student) })

        val colleges = collegeRepository.findAll()
        assertFalse(colleges.any { it.student.contains(student) })

        val interests = interestRepository.findAll()
        assertFalse(interests.any { it.student.contains(student) })
    }

}

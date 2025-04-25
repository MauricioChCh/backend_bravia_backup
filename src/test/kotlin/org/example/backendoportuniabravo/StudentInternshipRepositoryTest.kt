//package org.example.backendoportuniabravo
//
//import org.assertj.core.api.Assertions.assertThat
//import org.example.backendoportuniabravo.entities.Internship
//import org.example.backendoportuniabravo.repositories.InternshipRepository
//import org.example.backendoportuniabravo.repositories.StudentInternship
//import org.example.backendoportuniabravo.repositories.StudentInternshipRepository
//import org.junit.jupiter.api.BeforeEach
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import java.util.*
//import kotlin.test.Test
//
//@DataJpaTest
//class StudentInternshipRepositoryTest {
//
//    @Autowired
//    lateinit var studentInternshipRepository: StudentInternshipRepository
//
//    @Autowired
//    lateinit var internshipRepository: InternshipRepository
//
//    @BeforeEach
//    fun setup() {
//        // Crear pasantía de prueba
//        val internship = internshipRepository.save(
//            Internship(
//                title = "Test Internship",
//                company = "Test Corp",
//                companyId = 1,
//                location = "Test City",
//                publicationDate = Date(),
//                duration = "1 mes",
//                modality = "Test",
//                schedule = "Test",
//                requirements = "Test",
//                activities = "Test",
//                contact = "test@test.com",
//                link = "https://test.com"
//            )
//        )
//
//        // Crear bookmarks de prueba
//        studentInternshipRepository.saveAll(listOf(
//            StudentInternship(studentId = 1L, internshipId = internship.id!!, applied = true),
//            StudentInternship(studentId = 1L, internshipId = 2L, applied = false),
//            StudentInternship(studentId = 2L, internshipId = internship.id!!, applied = false)
//        ))
//    }
//
//    @Test
//    fun `findByStudentIdAndInternshipId should return specific bookmark`() {
//        val result = studentInternshipRepository.findByStudentIdAndInternshipId(1L, 2L)
//        assertThat(result).isPresent
//        assertThat(result.get().applied).isFalse()
//    }
//
//    @Test
//    fun `findByStudentId should return all bookmarks for student`() {
//        val result = studentInternshipRepository.findByStudentId(1L)
//        assertThat(result).hasSize(2)
//    }
//
//    @Test
//    fun `countByInternshipId should return number of bookmarks for internship`() {
//        val internship = internshipRepository.findByTitleContainingIgnoreCase("Test").first()
//        val result = studentInternshipRepository.countByInternshipId(internship.id!!)
//        assertThat(result).isEqualTo(2)
//    }
//
//    @Test
//    fun `deleteByStudentIdAndInternshipId should remove bookmark`() {
//        studentInternshipRepository.deleteByStudentIdAndInternshipId(1L, 2L)
//        val result = studentInternshipRepository.findByStudentIdAndInternshipId(1L, 2L)
//        assertThat(result).isEmpty()
//    }
//}
package org.example.backendoportuniabravo

import jakarta.transaction.Transactional
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.*

@SpringBootTest
@Transactional
class UserReportRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val profileRepository: ProfileRepository,
    val userReportRepository: UserReportRepository
) {

    @Test
    fun `crear userReport y probar queries`() {
        val user = userRepository.save(
            User(
                createDate = Date(),
                firstName = "Andrés",
                lastName = "Soto",
                email = "andres@email.com",
                password = "pass123",
                tokenExpired = false,
                enabled = true
            )
        )

        val reported = userRepository.save(
            User(
                createDate = Date(),
                firstName = "Jorge",
                lastName = "Muñoz",
                email = "jorge@email.com",
                password = "pass456",
                tokenExpired = false,
                enabled = true
            )
        )

        val report = userReportRepository.save(
            UserReports(
                user = user,
                userReported = reported,
                description = "Conducta inapropiada",
            )
        )

        // findByUserId
        val reportByUser = userReportRepository.findByUserId(user.id!!)
        assertNotNull(reportByUser)
        assertEquals("Conducta inapropiada", reportByUser?.description)

        // findByUserReportedId
        val reportByReported = userReportRepository.findByUserReportedId(reported.id!!)
        assertNotNull(reportByReported)
        assertEquals("Conducta inapropiada", reportByReported?.description)

        // existsByUserId
        assertTrue(userReportRepository.existsByUserId(user.id!!))

        // existsByUserReportedId
        assertTrue(userReportRepository.existsByUserReportedId(reported.id!!))

        // findByDescriptionContainingIgnoreCase
        val foundByDescription = userReportRepository.findByDescriptionContainingIgnoreCase("Conducta")
        assertEquals(1, foundByDescription.size)
        assertTrue(foundByDescription.first().description.contains("Conducta", ignoreCase = true))
    }

    @Test
    fun `no debe encontrar reportes inexistentes`() {
        val nonExistentId = 99999L

        assertNull(userReportRepository.findByUserId(nonExistentId))
        assertNull(userReportRepository.findByUserReportedId(nonExistentId))
        assertFalse(userReportRepository.existsByUserId(nonExistentId))
        assertFalse(userReportRepository.existsByUserReportedId(nonExistentId))
        assertTrue(userReportRepository.findByDescriptionContainingIgnoreCase("texto que no existe").isEmpty())
    }
}

private fun UserRepository.save(user: User) {}

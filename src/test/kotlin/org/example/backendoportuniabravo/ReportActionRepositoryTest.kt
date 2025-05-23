package org.example.backendoportuniabravo

import jakarta.transaction.Transactional
import junit.framework.TestCase.*
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Date
import kotlin.test.Test


@SpringBootTest
@Transactional
class ReportActionRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val profileRepository: ProfileRepository,
    val adminRepository: AdminRepository,
    val userReportRepository: UserReportRepository,
    val reportActionRepository: ReportActionRepository
) {

    @Test
    fun `crear reportAction y probar queries`() {
        // Crear usuarios
        val user = userRepository.save(
            User(
                createDate = Date(),
                firstName = "Lucía",
                lastName = "Fernández",
                email = "lucia@email.com",
                password = "123456",
                tokenExpired = false,
                enabled = true
            )
        )

        val reportedUser = userRepository.save(
            User(
                createDate = Date(),
                firstName = "Pedro",
                lastName = "Gómez",
                email = "pedro@email.com",
                password = "7891011",
                tokenExpired = false,
                enabled = true
            )
        )

        // Crear perfil y admin
        val profile = profileRepository.save(Profile(user = user, verified = true))
        val admin = adminRepository.save(Admin(profile = profile))

        // Crear userReport
        val userReport = userReportRepository.save(
            UserReports(
                user = user,
                userReported = reportedUser,
                description = "Publica contenido no deseado",
            )
        )

        // Crear ReportAction
        val action = reportActionRepository.save(
            ReportAction(
                admin = admin,
                userReport = userReport,
                action = "Advertencia enviada",

            )
        )

        // Probar findByAdminId
        val actionsByAdmin = reportActionRepository.findByAdminId(admin.id!!)
        assertEquals(1, actionsByAdmin.size)
        assertEquals("Advertencia enviada", actionsByAdmin.first().action)

        // Probar existsByAdminId
        assertTrue(reportActionRepository.existsByAdminId(admin.id!!))

        // Probar findByUserReportId
        val actionByReport = reportActionRepository.findByUserReportId(userReport.id!!)
        assertNotNull(actionByReport)
        assertEquals("Advertencia enviada", actionByReport?.action)

        // Probar existsByUserReportId
        assertTrue(reportActionRepository.existsByUserReportId(userReport.id!!))

        // Probar findByActionContainingIgnoreCase
        val actionsByText = reportActionRepository.findByActionContainingIgnoreCase("advertencia")
        assertEquals(1, actionsByText.size)
        assertEquals("Advertencia enviada", actionsByText.first().action)
    }

    @Test
    fun `no debe encontrar acciones inexistentes`() {
        val nonExistentId = 99999L

        assertTrue(reportActionRepository.findByAdminId(nonExistentId).isEmpty())
        assertFalse(reportActionRepository.existsByAdminId(nonExistentId))
        assertNull(reportActionRepository.findByUserReportId(nonExistentId))
        assertFalse(reportActionRepository.existsByUserReportId(nonExistentId))
        assertTrue(reportActionRepository.findByActionContainingIgnoreCase("acción no existente").isEmpty())
    }
}

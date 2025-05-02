package org.example.backendoportuniabravo

import org.example.backendoportuniabravo.entities.*
import org.example.backendoportuniabravo.repositories.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@Transactional
class AdminRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val profileRepository: ProfileRepository,
    val adminRepository: AdminRepository
) {

    @Test
    fun `crear admin y probar queries`() {
        // Crear usuario
        val user = userRepository.save(
            User(
                createDate = Date(),
                firstName = "Laura",
                lastName = "Gómez",
                email = "laura@email.com",
                password = "admin123",
                tokenExpired = false,
                enabled = true
            )
        )

        // Crear perfil
        val profile = profileRepository.save(
            Profile(user = user, verified = true)
        )

        // Crear admin con ese perfil
        val admin = adminRepository.save(
            Admin(profile = profile)
        )

        // Probar findByProfileId
        val fetchedAdmin = adminRepository.findByProfileId(profile.id!!)
        assertNotNull(fetchedAdmin)
        assertEquals(admin.id, fetchedAdmin?.id)

        // Probar existsByProfileId
        val exists = adminRepository.existsByProfileId(profile.id!!)
        assertTrue(exists)


    }
}

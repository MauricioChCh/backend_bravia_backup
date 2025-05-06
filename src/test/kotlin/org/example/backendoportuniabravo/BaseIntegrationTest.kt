package org.example.backendoportuniabravo

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

//@SpringBootTest
@Testcontainers
abstract class BaseIntegrationTest {

    companion object {
        private val postgres = SharedPostgresContainer.instance


        @JvmStatic
        @DynamicPropertySource
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }
}

//Se hace separado para evitar que se inicie la base de datos en cada test
object SharedPostgresContainer {
    val instance: PostgreSQLContainer<*> by lazy {
        PostgreSQLContainer("postgres:15").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
            start() // 🔥 importante
        }
    }
}
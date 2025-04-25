//package org.example.backendoportuniabravo
//
//import org.assertj.core.api.Assertions.assertThat
//import org.example.backendoportuniabravo.entities.BusinessArea
//import org.example.backendoportuniabravo.repositories.BusinessAreaRepository
//import org.junit.jupiter.api.BeforeEach
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import kotlin.test.Test
//
//@DataJpaTest
//class BusinessAreaRepositoryTest {
//
//    @Autowired
//    lateinit var businessAreaRepository: BusinessAreaRepository
//
//    @BeforeEach
//    fun setup() {
//        businessAreaRepository.saveAll(listOf(
//            BusinessArea(name = "Tecnología"),
//            BusinessArea(name = "Finanzas"),
//            BusinessArea(name = "Salud")
//        ))
//    }
//
//    @Test
//    fun `findByNameIgnoreCase should find area case insensitive`() {
//        val result = businessAreaRepository.findByNameIgnoreCase("tecnologia")
//        assertThat(result).isPresent
//        assertThat(result.get().name).isEqualTo("Tecnología")
//    }
//
//    @Test
//    fun `findByNameIgnoreCase should return empty for non-existent area`() {
//        val result = businessAreaRepository.findByNameIgnoreCase("inexistente")
//        assertThat(result).isEmpty()
//    }
//}
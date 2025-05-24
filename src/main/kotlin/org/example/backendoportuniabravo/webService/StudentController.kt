package org.example.backendoportuniabravo.webService

import kotlinx.coroutines.runBlocking
import org.example.backendoportuniabravo.dto.StudentCreateRequestDTO
import org.example.backendoportuniabravo.dto.StudentCurriculumResponseDTO
import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.service.AIService
import org.example.backendoportuniabravo.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.students}")
class StudentController(
    private val service: StudentService,
    private val aiService: AIService
) {



    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<StudentResponseDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<StudentResponseDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/curriculum/ai")
    fun getCurriculumWithAI(@PathVariable id: Long): Map<String, Any> = runBlocking {
        val curriculum = service.returnStudentCurriculum(id)

        val prompt = buildPromptFromCurriculum(curriculum)
        val aiFeedback = aiService.generateResponse(prompt)

        mapOf(
            "curriculum" to curriculum,
            "aiFeedback" to aiFeedback
        )
    }

    private fun buildPromptFromCurriculum(curriculum: StudentCurriculumResponseDTO): String {
        val builder = StringBuilder()

        builder.appendLine("Información del estudiante:")
        builder.appendLine("Nombre: ${curriculum.userInput.firstName} ${curriculum.userInput.lastName}")
        builder.appendLine("Email: ${curriculum.userInput.email}")
        builder.appendLine("Descripción: ${curriculum.description}")
        builder.appendLine("Centro académico: ${curriculum.academicCenter}")
        builder.appendLine()

        builder.appendLine("🎯 Carreras:")
        curriculum.careers.forEach {
            builder.appendLine("- ${it.career}")
        }

        builder.appendLine("\n🎓 Certificaciones:")
        curriculum.certifications.forEach {
            builder.appendLine("- ${it.name} por ${it.organization} en ${it.date}")
        }

        builder.appendLine("\n💼 Experiencias:")
        curriculum.experiences.forEach {
            builder.appendLine("- ${it.name}: ${it.description}")
        }

        builder.appendLine("\n🛠️ Habilidades:")
        curriculum.skills.forEach {
            builder.appendLine("- ${it.name}: ${it.description}")
        }

        builder.appendLine("\n🎲 Hobbies:")
        curriculum.hobbies.forEach {
            builder.appendLine("- ${it.name}")
        }

        builder.appendLine("\n👉 Por favor, analiza este perfil profesional de estudiante y sugiere oportunidades de mejora, fortalezas clave, y posibles rutas laborales o académicas recomendadas.")

        return builder.toString()
    }
}

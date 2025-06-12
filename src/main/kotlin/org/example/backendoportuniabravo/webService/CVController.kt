package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.CVGenerationRequest
import org.example.backendoportuniabravo.dto.CVGenerationResponse
import org.example.backendoportuniabravo.service.AIService
import org.example.backendoportuniabravo.service.CVService
import org.example.backendoportuniabravo.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.iacv}")
class CVController(
    private val cvService: CVService,
    private val studentService: StudentService,
    private val aiService: AIService
) {

    @PostMapping("/generate")
    suspend fun generateCV(@RequestBody request: CVGenerationRequest): ResponseEntity<CVGenerationResponse> {
        return try {
            // 1. Obtener el currículum del estudiante

            // 2. Generar el prompt para la IA
            val curriculum = studentService.returnStudentCurriculum(request.studentId).let {
                mapOf(
                    "skills" to it.skills,
                    "description" to it.description,
                    "careers" to it.careers,
                    "hoobies" to it.hobbies,
                    "academic center" to it.academicCenter,
                    "experiences" to it.experiences,
                )
            }
            // 3. Obtener respuesta de la IA
            val prompt = buildCVPrompt(curriculum, request.additionalInfo, request.template)
            val aiResponse = aiService.generateResponse(prompt)

            // 4. Generar el CV en el formato solicitado
            val result = cvService.generateCVFromAIResponse(
                studentId = request.studentId,
                aiResponse = aiResponse,
                format = request.format
            )

            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                CVGenerationResponse(
                    pdfUrl = null,
                    latexSource = null,
                    aiFeedback = "Error generating CV: ${e.message}",
                    message = "Failed to generate CV"
                )
            )
        }
    }

    private fun buildCVPrompt(
        curriculum: Map<String, Any>,
        additionalInfo: String,
        template: String
    ): String {
        return """
            Act as a professional CV generator. Based on the following student curriculum data 
            and additional instructions, generate a well-structured CV in $template style.
            
            Student Curriculum Data:
            ${curriculum.entries.joinToString("\n") { "${it.key}: ${it.value}" }}
            
            Additional Instructions from Student:
            $additionalInfo
            
            Please generate:
            1. A professional summary section
            2. Education section with proper formatting
            3. Work experience section with bullet points
            4. Skills section organized by categories
            5. Any additional sections that would be relevant
            
            Format the output as a LaTeX document ready for compilation to PDF.
            Only respond with the LaTeX code, no additional explanations.
        """.trimIndent()
    }
}
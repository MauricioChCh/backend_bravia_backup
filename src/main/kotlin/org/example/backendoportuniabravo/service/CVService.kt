package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.CVGenerationResponse
import org.springframework.stereotype.Service

@Service
class CVService(
    private val fileStorageService: FileStorageService,
    private val latexCompilerService: LatexCompilerService
) {
    suspend fun generateCVFromAIResponse(
        studentId: Long,
        aiResponse: String,
        format: String
    ): CVGenerationResponse {
        return when (format.uppercase()) {
            "PDF" -> {
                // 1. Guardar el LaTeX generado por la IA
                val latexFile = fileStorageService.saveLatexFile(studentId, aiResponse)

                // 2. Compilar a PDF
                val pdfFile = latexCompilerService.compileToPdf(latexFile)

                // 3. Guardar PDF y obtener URL
                val pdfUrl = fileStorageService.savePdfFile(studentId, pdfFile)

                CVGenerationResponse(
                    pdfUrl = pdfUrl,
                    latexSource = aiResponse,
                    aiFeedback = "CV generated successfully",
                    message = "PDF generated and ready for download"
                )
            }
            "LATEX" -> {
                val latexUrl = fileStorageService.saveLatexFile(studentId, aiResponse)
                CVGenerationResponse(
                    pdfUrl = null,
                    latexSource = aiResponse,
                    aiFeedback = "LaTeX CV generated successfully",
                    message = "LaTeX source ready for download"
                )
            }
            else -> throw IllegalArgumentException("Unsupported format: $format")
        }
    }
}
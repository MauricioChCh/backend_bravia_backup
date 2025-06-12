import org.example.backendoportuniabravo.dto.CVGenerationResponse
import org.example.backendoportuniabravo.entity.Experience
import org.example.backendoportuniabravo.entity.Skill
import org.example.backendoportuniabravo.entity.Student
import org.example.backendoportuniabravo.service.GeminiAIService
import org.springframework.stereotype.Service

// Enums para definir tipos de templates y formatos
enum class CVTemplate {
    MODERN_PROFESSIONAL,
    CLASSIC_ACADEMIC,
    CREATIVE_DESIGN,
    TECH_MINIMAL,
    BANKING_CORPORATE
}

enum class CVFormat {
    PDF,
    LATEX,
    HTML,
    MARKDOWN
}

// Configuración de template
data class TemplateConfig(
    val template: CVTemplate,
    val colorScheme: String = "blue",
    val fontSize: String = "11pt",
    val paperSize: String = "a4paper",
    val includeSections: List<CVSection> = CVSection.values().toList(),
    val customStyles: Map<String, String> = emptyMap()
)

enum class CVSection {
    PERSONAL_INFO,
    PROFESSIONAL_SUMMARY,
    EDUCATION,
    EXPERIENCE,
    SKILLS,
    CERTIFICATIONS,
    LANGUAGES,
    HOBBIES,
    CONTACTS,
    PROJECTS
}

// DTO mejorado para requests
data class CVRequestDTO(
    val Student: Student,
    val templateConfig: TemplateConfig,
    val additionalInfo: String = "",
    val aiEnhancements: AIEnhancementOptions = AIEnhancementOptions()
)

data class AIEnhancementOptions(
    val enhanceDescriptions: Boolean = true,
    val generateSummary: Boolean = true,
    val optimizeForATS: Boolean = true,
    val targetJobType: String? = null,
    val improveBulletPoints: Boolean = true
)

// Service principal mejorado
@Service
class EnhancedCVService(
    private val aiService: GeminiAIService,
    private val templateService: CVTemplateService,
    private val pdfGenerationService: PDFGenerationService
) {

    suspend fun generateCV(request: CVRequestDTO): CVGenerationResponse {
        // 1. Procesar y mejorar el contenido con IA
        val enhancedProfile = enhanceProfileWithAI(request.Student, request.aiEnhancements)

        // 2. Generar contenido adicional si es necesario
        val additionalContent = if (request.aiEnhancements.generateSummary) {
            generateProfessionalSummary(enhancedProfile, request.additionalInfo)
        } else ""

        // 3. Aplicar template
        val latexContent = templateService.generateLatexContent(
            profile = enhancedProfile,
            config = request.templateConfig,
            additionalContent = additionalContent
        )

        // 4. Generar PDF
        val pdfUrl = pdfGenerationService.generatePDF(latexContent)

        return CVGenerationResponse(
            pdfUrl = pdfUrl,
            latexSource = if (request.templateConfig.template == CVTemplate.TECH_MINIMAL) latexContent else null,
            aiFeedback = "AI feedback not available", // Default value
            message = "CV generated successfully" // Default value
        )
    }

    private suspend fun enhanceProfileWithAI(
        profile: Student,
        options: AIEnhancementOptions
    ): Student {
        if (!options.enhanceDescriptions) return profile

        val enhancedExperiences = profile.experiences?.map { experience ->
            val enhancedDescription = aiService.generateResponse(
                buildPrompt("enhance_experience", experience, options.targetJobType)
            )
            experience.copy(description = enhancedDescription)
        }

        val enhancedSkills = if (options.optimizeForATS) {
            profile.skills?.map { skill ->
                // Mejorar keywords para ATS
                val optimizedDescription = aiService.generateResponse(
                    buildPrompt("optimize_skill_ats", skill, options.targetJobType)
                )
                skill.copy(description = optimizedDescription)
            }
        } else profile.skills

        return profile.copy(
            experiences = enhancedExperiences?.toMutableList(),
            skills = enhancedSkills?.toMutableList()
        )
    }

    private suspend fun generateProfessionalSummary(
        profile: Student,
        additionalInfo: String
    ): String {
        val prompt = """
            Genera un resumen profesional conciso y atractivo basado en:
           Nombre: " "
            Experiencia: ${profile.experiences?.size ?: 0} posiciones
            Habilidades principales: ${profile.skills?.take(5)?.joinToString { it.name ?: "" }}
            Información adicional: $additionalInfo
            
            El resumen debe ser de 2-3 líneas, profesional y destacar fortalezas clave.
        """.trimIndent()

        return aiService.generateResponse(prompt)
    }

    private fun buildPrompt(type: String, data: Any, targetJob: String?): String {
        return when (type) {
            "enhance_experience" -> {
                val exp = data as Experience
                """
            Mejora la siguiente descripción de experiencia laboral para un CV:
            Posición: ${exp.name}
            Descripción actual: ${exp.description}
            ${targetJob?.let { "Trabajo objetivo: $it" } ?: ""}
            
            Haz la descripción más impactante, usando verbos de acción y métricas cuando sea posible.
            Máximo 3 puntos clave en formato bullet points.
            """.trimIndent()
            }
            "optimize_skill_ats" -> {
                val skill = data as Skill
                """
            Optimiza esta habilidad para sistemas ATS:

            ${targetJob?.let { "Para trabajo en: $it" } ?: ""}
            
            Incluye palabras clave relevantes y variaciones comunes de la habilidad.
            """.trimIndent()
            }
            else -> ""
        }
    }
}

// Template Service mejorado
@Service
class CVTemplateService {

    fun generateLatexContent(
        profile: Student,
        config: TemplateConfig,
        additionalContent: String = ""
    ): String {
        return when (config.template) {
            CVTemplate.MODERN_PROFESSIONAL -> generateModernTemplate(profile, config, additionalContent)
            CVTemplate.CLASSIC_ACADEMIC -> generateAcademicTemplate(profile, config, additionalContent)
            CVTemplate.CREATIVE_DESIGN -> generateCreativeTemplate(profile, config, additionalContent)
            CVTemplate.TECH_MINIMAL -> generateTechTemplate(profile, config, additionalContent)
            CVTemplate.BANKING_CORPORATE -> generateBankingTemplate(profile, config, additionalContent)
        }
    }

    private fun generateModernTemplate(
        profile: Student,
        config: TemplateConfig,
        additionalContent: String
    ): String {
        return """
            \documentclass[${config.fontSize},${config.paperSize}]{moderncv}
            \moderncvstyle{banking}
            \moderncvcolor{${config.colorScheme}}
            \usepackage[scale=0.75]{geometry}
            \usepackage[utf8]{inputenc}
            
         
            ${generateSectionContent(profile, config.includeSections)}
            
            \end{document}
        """.trimIndent()
    }

    private fun generateAcademicTemplate(
        profile: Student,
        config: TemplateConfig,
        additionalContent: String
    ): String {
        return """
            \documentclass[${config.fontSize},${config.paperSize}]{article}
            \usepackage[margin=1in]{geometry}
            \usepackage[utf8]{inputenc}
            \usepackage{enumitem}
            \usepackage{titlesec}
            
            \titleformat{\section}{\large\bfseries}{\thesection}{1em}{}
            \titleformat{\subsection}{\normalsize\bfseries}{\thesubsection}{1em}{}
            
            \begin{document}
            
          
            
            \hrule
            \vspace{0.3cm}
            
            ${generateAcademicSections(profile, config.includeSections)}
            
            \end{document}
        """.trimIndent()
    }

    private fun generateTechTemplate(
        profile: Student,
        config: TemplateConfig,
        additionalContent: String
    ): String {
        return """
            \documentclass[${config.fontSize},${config.paperSize}]{article}
            \usepackage[margin=0.8in]{geometry}
            \usepackage[utf8]{inputenc}
            \usepackage{xcolor}
            \usepackage{fontawesome}
            \usepackage{hyperref}
            
            \definecolor{techblue}{RGB}{0,123,191}
            \definecolor{techgray}{RGB}{64,64,64}
            
            \begin{document}
            
          
            \vspace{0.5cm}
            \textcolor{techblue}{\hrule height 2pt}
            \vspace{0.3cm}
            
            ${generateTechSections(profile, config.includeSections)}
            
            \end{document}
        """.trimIndent()
    }

    private fun generateCreativeTemplate(
        profile: Student,
        config: TemplateConfig,
        additionalContent: String
    ): String {
        // Template creativo con más elementos visuales
        return generateModernTemplate(profile, config, additionalContent) // Simplificado por espacio
    }

    private fun generateBankingTemplate(
        profile: Student,
        config: TemplateConfig,
        additionalContent: String
    ): String {
        // Template corporativo conservador
        return generateAcademicTemplate(profile, config, additionalContent) // Simplificado por espacio
    }

    private fun generateSectionContent(profile: Student, sections: List<CVSection>): String {
        return sections.joinToString("\n\n") { section ->
            when (section) {
                CVSection.PROFESSIONAL_SUMMARY -> generateSummarySection(profile)
                CVSection.SKILLS -> generateSkillsSection(profile)
                CVSection.CERTIFICATIONS -> generateCertificationsSection(profile)
                CVSection.LANGUAGES -> generateLanguagesSection(profile)
                CVSection.HOBBIES -> generateHobbiesSection(profile)
                else -> ""
            }
        }
    }

    private fun generateSummarySection(profile: Student): String {
        return if (profile.description?.isNotBlank() == true) {
            """
            \section{Resumen Profesional}
            ${profile.description}
            """.trimIndent()
        } else ""
    }



    private fun generateSkillsSection(profile: Student): String {
        val skills = profile.skills
        if (skills.isNullOrEmpty()) return ""

        return """
        
    """.trimIndent()
    }

    private fun generateCertificationsSection(profile: Student): String {
        val certifications = profile.certifications
        if (certifications.isNullOrEmpty()) return ""

        return """
        \section{Certificaciones}
       
    """.trimIndent()
    }

    private fun generateLanguagesSection(profile: Student): String {
        val languages = profile.languages
        if (languages.isNullOrEmpty()) return ""

        return """
        \section{Idiomas}
        
    """.trimIndent()
    }

    private fun generateHobbiesSection(profile: Student): String {
        val hobbies = profile.hobbies
        if (hobbies.isNullOrEmpty()) return ""

        return """
        \section{Intereses y Hobbies}
        \\cvitem{}{${hobbies.joinToString(", ") { it.name ?: "" }}}
    """.trimIndent()
    }

    // Métodos específicos para templates académicos y tech (simplificados por espacio)
    private fun generateAcademicSections(profile: Student, sections: List<CVSection>): String {
        return generateSectionContent(profile, sections).replace("\\section", "\\section*")
    }

    private fun generateTechSections(profile: Student, sections: List<CVSection>): String {
        return generateSectionContent(profile, sections)
            .replace("\\section{", "\\noindent\\textcolor{techblue}{\\large\\textbf{")
            .replace("}", "}}")
    }
}

// Service para generación de PDF
@Service
class PDFGenerationService {

    suspend fun generatePDF(latexContent: String): String {
        // Aquí integrarías con un servicio de compilación LaTeX
        // Por ejemplo: Overleaf API, TeXLive local, o un microservicio

        // Simulación de generación de PDF
        val fileName = "cv_${System.currentTimeMillis()}.pdf"
        val pdfUrl = compileLatexToPDF(latexContent, fileName)

        return pdfUrl
    }

    private suspend fun compileLatexToPDF(latex: String, fileName: String): String {
        // Implementación real dependería de tu infraestructura
        // Opciones:
        // 1. Servicio local con TeXLive
        // 2. API externa como Overleaf
        // 3. Contenedor Docker con LaTeX
        // 4. Servicio cloud como AWS Lambda con LaTeX layer

        return "https://your-storage.com/cvs/$fileName"
    }
}
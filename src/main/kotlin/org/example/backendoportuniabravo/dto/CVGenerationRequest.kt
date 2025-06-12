package org.example.backendoportuniabravo.dto

data class CVGenerationRequest(
    val studentId: Long,
    val additionalInfo: String,
    val format: String = "PDF", // PDF o LATEX
    val template: String = "MODERN" // Puedes tener varios templates
)
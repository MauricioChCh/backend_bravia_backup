package org.example.backendoportuniabravo.dto

data class CVGenerationResponse(
    val pdfUrl: String?,
    val latexSource: String?,
    val aiFeedback: String,
    val message: String
)
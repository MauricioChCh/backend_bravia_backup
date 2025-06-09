package org.example.backendoportuniabravo.service

interface AIService {
    suspend fun generateResponse(prompt: String): String
}

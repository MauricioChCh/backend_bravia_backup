package org.example.backendoportuniabravo.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


interface AIService {
    suspend fun generateResponse(prompt: String): String
}

@Service
class GeminiAIService(
    @Value("\${gemini.api.key}") private val apiKey: String,
    @Value("\${gemini.api.url}") private val apiUrl: String
) : AIService {
    private val webClient = WebClient.builder()
        .baseUrl(apiUrl)
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build()

    override suspend fun generateResponse(prompt: String): String {
        val requestBody = mapOf(
            "contents" to listOf(
                mapOf("parts" to listOf(mapOf("text" to prompt)))
            )
        )

        return webClient.post()
            .uri("/models/gemini-2.0-flash:generateContent?key=$apiKey")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorReturn("Error al llamar a la API de Gemini")
            .awaitSingle()
    }
}

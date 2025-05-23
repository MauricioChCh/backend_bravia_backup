package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.service.AIService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.ia}")
class AIController(
    private val aiService: AIService
) {

    @PostMapping("/generate")
    suspend fun generate(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        val prompt = body["prompt"] ?: return ResponseEntity.badRequest().body("Falta el campo 'prompt'")
        val response = aiService.generateResponse(prompt)
        return ResponseEntity.ok(response)
    }
}
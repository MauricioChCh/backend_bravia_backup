package org.example.backendoportuniabravo.controller

import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.service.InternshipService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/internships")
class InternshipController(private val service: InternshipService) {

    @PostMapping
    fun create(@RequestBody dto: InternshipRequestDTO): ResponseEntity<InternshipResponseDTO> {
        val saved = service.createInternship(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @GetMapping
    fun list(): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.getAll())
}

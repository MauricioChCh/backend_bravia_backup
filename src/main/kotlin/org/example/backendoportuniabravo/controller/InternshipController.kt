package org.example.backendoportuniabravo.controller

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
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

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody dto: InternshipRequestDTO
    ): ResponseEntity<InternshipResponseDTO> {
        val updated = service.update(id, dto)
        return ResponseEntity.ok(updated)
    }
    @PatchMapping("/{id}")
    fun patchInternship(
        @PathVariable id: Long,
        @RequestBody dto: InternshipPatchDTO
    ): ResponseEntity<InternshipResponseDTO> {
        val updated = service.patch(id, dto)
        return ResponseEntity.ok(updated)
    }



    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<InternshipResponseDTO> {
        val internship = service.findById(id)
        return ResponseEntity.ok(internship)
    }

    @GetMapping("/search")
    fun search(@RequestParam query: String): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.search(query))


    //Pruebas
    @GetMapping("/recommended/{studentId}")
    fun getRecommended(@PathVariable studentId: Long): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.getRecommendedForStudent(studentId))



}

package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.InternshipPatchDTO
import org.example.backendoportuniabravo.dto.InternshipRequestDTO
import org.example.backendoportuniabravo.dto.InternshipResponseDTO
import org.example.backendoportuniabravo.service.InternshipService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.http.MediaType


@RestController
@RequestMapping("\${url.internships}")
class InternshipController(private val service: InternshipService) {

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody dto: InternshipRequestDTO): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.createInternship(dto))

    @GetMapping
    fun list(): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.getAll())

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: InternshipRequestDTO): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.ok(service.update(id, dto))

    @PatchMapping("{id}")
    fun patchInternship(@PathVariable id: Long, @RequestBody dto: InternshipPatchDTO): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.ok(service.patch(id, dto))

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.ok(service.findById(id))

    @GetMapping("/search")
    fun search(@RequestParam query: String): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.search(query))

    @GetMapping("/recommended/{studentId}")
    fun getRecommended(@PathVariable studentId: Long): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.getRecommendedForStudent(studentId))
}

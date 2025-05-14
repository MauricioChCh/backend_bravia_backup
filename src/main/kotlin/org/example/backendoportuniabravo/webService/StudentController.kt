package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.StudentRequestDTO
import org.example.backendoportuniabravo.dto.StudentResponseDTO
import org.example.backendoportuniabravo.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val service: StudentService
) {

    @PostMapping
    fun create(@RequestBody dto: StudentRequestDTO): ResponseEntity<StudentResponseDTO> {
        val response = service.create(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<StudentResponseDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<StudentResponseDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}

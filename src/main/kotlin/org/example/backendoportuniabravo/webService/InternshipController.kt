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

    /**
     * Creates a new internship using the provided data.
     * @param dto the internship request payload
     * @return the created internship and HTTP 201 status
     */
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody dto: InternshipRequestDTO): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.createInternship(dto))

    /**
     * Retrieves all internships.
     * @return a list of internship responses
     */
    @GetMapping
    fun list(): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.getAll())

    /**
     * Updates an existing internship by its ID.
     * @param id the internship ID
     * @param dto the full update data
     * @return the updated internship response
     */
    @Throws(NoSuchElementException::class)
    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: InternshipRequestDTO): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.ok(service.update(id, dto))

    /**
     * Applies a partial update to an internship.
     * @param id the internship ID
     * @param dto the partial fields to update
     * @return the updated internship
     */
    @Throws(NoSuchElementException::class)
    @PatchMapping("{id}")
    fun patchInternship(@PathVariable id: Long, @RequestBody dto: InternshipPatchDTO): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.ok(service.patch(id, dto))

    /**
     * Retrieves a single internship by its ID.
     * @param id the internship ID
     * @return the internship response
     */
    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<InternshipResponseDTO> =
        ResponseEntity.ok(service.findById(id))

    /**
     * Searches internships by a query string (in title or requirements).
     * @param query the keyword to search for
     * @return a list of matching internships
     */
    @Throws(NoSuchElementException::class)
    @GetMapping("/search")
    fun search(@RequestParam query: String): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.search(query))

    /**
     * Returns internship recommendations for a given student.
     * @param studentId the student’s ID
     * @return a list of recommended internships
     */
    @GetMapping("/recommended/{studentId}")
    fun getRecommended(@PathVariable studentId: Long): ResponseEntity<List<InternshipResponseDTO>> =
        ResponseEntity.ok(service.getRecommendedForStudent(studentId))
}

package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.AdminRequestDTO
import org.example.backendoportuniabravo.dto.AdminResponseDTO
import org.example.backendoportuniabravo.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.admins}")
class AdminController(
    private val adminService: AdminService
) {

    /**
     * Retrieves all admins in the system.
     * @return list of admin responses
     */
    @GetMapping
    fun listAdmins(): ResponseEntity<List<AdminResponseDTO>> =
        ResponseEntity.ok(adminService.getAdmins() ?: emptyList())

    /**
     * Retrieves a specific admin by their ID.
     * @param id the admin ID
     * @return the admin response
     */
    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<AdminResponseDTO> {
        val admin = adminService.getAdminById(id)
        return if (admin != null) ResponseEntity.ok(admin)
        else ResponseEntity.notFound().build()
    }

    /**
     * Creates a new admin with the provided profile ID.
     * @param dto the admin request payload
     * @return the ID of the created admin
     */
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody dto: AdminRequestDTO): ResponseEntity<Any> {
        val result = adminService.addAdmin(dto.profileId)
        return if (result != null) {
            ResponseEntity.status(HttpStatus.CREATED).body(mapOf("id" to result))
        } else {
            ResponseEntity.badRequest().body("No se pudo crear el admin. Verifica el profileId.")
        }
    }

    /**
     * Deletes admin privileges for a profile.
     * @param profileId the profile ID associated with the admin
     * @return 204 if deleted, 404 otherwise
     */
    @DeleteMapping("{profileId}")
    fun deleteAdminByProfileId(@PathVariable profileId: Long): ResponseEntity<Void> {
        val removed = adminService.deleteAdmin(profileId)
        return if (removed) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }//Pensar luego logica para hacer el delete, preguntar si este debe de desactivar o borrar
}

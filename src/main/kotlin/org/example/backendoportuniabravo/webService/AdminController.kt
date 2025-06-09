package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.AdminRequestDTO
import org.example.backendoportuniabravo.dto.AdminResponseDTO
import org.example.backendoportuniabravo.dto.ReportActionRequestDTO
import org.example.backendoportuniabravo.dto.ReportActionResponseDTO
import org.example.backendoportuniabravo.mapper.ReportActionMapper
import org.example.backendoportuniabravo.service.AdminService
import org.example.backendoportuniabravo.service.ReportActionService
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

@RestController
@RequestMapping("\${url.ReportActions}")
class ReportActionController(
    private val reportActionService: ReportActionService,
    private val mapper: ReportActionMapper
) {

/*    @PostMapping
    fun createReportAction(@RequestBody requestDTO: ReportActionRequestDTO ): ResponseEntity<ReportActionResponseDTO> {
        val createdEntity = reportActionService.createAction(requestDTO)
        val responseDTO = mapper.toResponseDto(createdEntity)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO)
    }

    @GetMapping
    fun getAllActions(): ResponseEntity<List<ReportActionResponseDTO>> {
        val actions = reportActionService.getAllActions()
        val responseList = actions.map { mapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }

    @GetMapping("/admin/{adminId}")
    fun getActionsByAdmin(@PathVariable adminId: Long): ResponseEntity<List<ReportActionResponseDTO>> {
        val actions = reportActionService.getActionsByAdminId(adminId)
        val responseList = actions.map { mapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }

    @GetMapping("/report/{reportId}")
    fun getActionByUserReportId(@PathVariable reportId: Long): ResponseEntity<ReportActionResponseDTO> {
        val action = reportActionService.getActionByUserReportId(reportId)
        return if (action != null) {
            ResponseEntity.ok(mapper.toResponseDto(action))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search")
    fun searchByAction(@RequestParam keyword: String): ResponseEntity<List<ReportActionResponseDTO>> {
        val actions = reportActionService.searchByActionText(keyword)
        val responseList = actions.map { mapper.toResponseDto(it) }
        return ResponseEntity.ok(responseList)
    }*/

    @GetMapping
    fun getAllActions(): ResponseEntity<List<ReportActionResponseDTO>> {
        val actions = reportActionService.getAllActions()
        val dtoList = actions.map { mapper.toResponseDto(it) }
        return ResponseEntity.ok(dtoList)
    }

    @GetMapping("/{id}")
    fun getActionById(@PathVariable id: Long): ResponseEntity<ReportActionResponseDTO> {
        val action = reportActionService.getActionById(id)
        return if (action != null) {
            ResponseEntity.ok(mapper.toResponseDto(action))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/admin/{adminId}")
    fun getActionsByAdminId(@PathVariable adminId: Long): ResponseEntity<List<ReportActionResponseDTO>> {
        val actions = reportActionService.getActionsByAdminId(adminId)
        val dtoList = actions.map { mapper.toResponseDto(it) }
        return ResponseEntity.ok(dtoList)
    }

    @GetMapping("/exists/admin/{adminId}")
    fun existsByAdminId(@PathVariable adminId: Long): ResponseEntity<Boolean> {
        return ResponseEntity.ok(reportActionService.existsByAdminId(adminId))
    }

    @GetMapping("/report/{userReportId}")
    fun getActionByUserReportId(@PathVariable userReportId: Long): ResponseEntity<ReportActionResponseDTO> {
        val action = reportActionService.getActionByUserReportId(userReportId)
        return if (action != null) {
            ResponseEntity.ok(mapper.toResponseDto(action))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/exists/report/{userReportId}")
    fun existsByUserReportId(@PathVariable userReportId: Long): ResponseEntity<Boolean> {
        return ResponseEntity.ok(reportActionService.existsByUserReportId(userReportId))
    }

    @GetMapping("/search")
    fun searchByActionText(@RequestParam query: String): ResponseEntity<List<ReportActionResponseDTO>> {
        val results = reportActionService.searchByActionText(query)
        val dtoList = results.map { mapper.toResponseDto(it) }
        return ResponseEntity.ok(dtoList)
    }

    @PostMapping
    fun createAction(@RequestBody requestDTO: ReportActionRequestDTO): ResponseEntity<ReportActionResponseDTO> {
        val created = reportActionService.createAction(requestDTO)
        val responseDTO = mapper.toResponseDto(created)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO)
    }

    @DeleteMapping("/{id}")
    fun deleteAction(@PathVariable id: Long): ResponseEntity<Void> {
        reportActionService.deleteAction(id)
        return ResponseEntity.noContent().build()
    }
}
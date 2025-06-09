package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.ReportActionRequestDTO
import org.example.backendoportuniabravo.dto.ReportActionResponseDTO
import org.example.backendoportuniabravo.mapper.ReportActionMapper
import org.example.backendoportuniabravo.service.ReportActionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/*
@RestController
@RequestMapping("\${url.ReportActions}")
class ReportActionController(
    private val reportActionService: ReportActionService,
    private val mapper: ReportActionMapper
) {

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
}*/

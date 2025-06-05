package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.UserReportRequestDTO
import org.example.backendoportuniabravo.dto.UserReportResponseDTO
import org.example.backendoportuniabravo.mapper.UserReportMapper
import org.example.backendoportuniabravo.service.AIService
import org.example.backendoportuniabravo.service.AdminService
import org.example.backendoportuniabravo.service.UserReportService
import org.example.backendoportuniabravo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.userReports}")
class UserReportController(
    private val userReportService: UserReportService,
    private val userReportMapper: UserReportMapper,
    private val userService: UserService
) {
    @GetMapping
    fun getAllReports(): ResponseEntity<List<UserReportResponseDTO>> {

        return ResponseEntity.ok(userReportMapper.toResponseDTOList(userReportService.findAll()))
    }

    @PostMapping
    fun createReport(@RequestBody requestDTO: UserReportRequestDTO): ResponseEntity<UserReportResponseDTO> {
        val report = userReportMapper.toEntity(requestDTO, userService.findById(requestDTO.reporterId) , userService.findById(requestDTO.userReportedId ) )
        val saved = userReportService.save(report)
        val response = userReportMapper.toResponseDTO(saved)
        return ResponseEntity.ok(response)
    }
}
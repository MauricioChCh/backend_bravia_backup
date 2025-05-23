package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.CompanyUserInput
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.access}")
class AccessController(
    private val companyService: CompanyService
) {
    @PostMapping("/signup/company")
    fun createCompany(@RequestBody company: CompanyUserInput) : ResponseEntity<CompanyUserResult>? {
        val saved = companyService.create(company)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }
}
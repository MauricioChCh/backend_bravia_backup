package org.example.backendoportuniabravo.controller

import org.example.backendoportuniabravo.dto.CompanyUserInput
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.dto.CompanyUserUpdate
import org.example.backendoportuniabravo.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/companies")
class CompanyController (private val service: CompanyService) {


    @GetMapping("{id}")
    @ResponseBody
    fun getCompanyById(@PathVariable id : Long) = service.findById(id)

    @PostMapping
    fun createCompany(@RequestBody company: CompanyUserInput) : ResponseEntity<CompanyUserResult>? {
        val saved = service.create(company)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @PutMapping
    @ResponseBody
    fun updateCompany(@RequestBody company: CompanyUserUpdate) = service.update(company)

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteCompany(@PathVariable id: Long) = service.deleteById(id)

}
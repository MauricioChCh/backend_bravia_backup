package org.example.backendoportuniabravo.controller

import org.example.backendoportuniabravo.dto.*
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

    @PatchMapping("{id}/name")
    fun updateCompanyName(@PathVariable id: Long, @RequestBody companyName: CompanyNameUpdate) : ResponseEntity<CompanyNameResult>? {
        val updated = service.updateName(id, companyName)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @PatchMapping("{id}/description")
    fun updateCompanyDescription(@PathVariable id: Long, @RequestBody companyDescription: CompanyDescriptionUpdate) : ResponseEntity<CompanyDescriptionResult>? {
        val updated = service.updateDescription(id, companyDescription)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteCompany(@PathVariable id: Long) = service.deleteById(id)

}
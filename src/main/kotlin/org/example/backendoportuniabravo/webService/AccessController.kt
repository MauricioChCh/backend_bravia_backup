package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.service.AccessService
import org.example.backendoportuniabravo.service.CompanyService
import org.example.backendoportuniabravo.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.access}")
class AccessController(
    private val companyService: CompanyService,
    private val studentService: StudentService,
    private val accessService: AccessService,
) {
    @PostMapping("/signup/company")
    fun createCompany(@RequestBody company: CompanyUserInput) : ResponseEntity<CompanyUserResult>? {
        val saved = companyService.create(company)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }
    @PostMapping("/signup/student")
    fun create(@RequestBody dto: StudentRegister): ResponseEntity<StudentResponseDTO> {
//        val response = studentService.create(dto) // TODO el metodo de create no esta acorde con el frontend
        val response = studentService.registerStudent(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/signup/business_areas")
    fun getBusinessAreas(): ResponseEntity<List<BusinessAreaDetails>> {
        val businessAreas = accessService.getBusinessAreas()
        return ResponseEntity.ok(businessAreas)
    }

    @GetMapping("/signup/colleges")
    fun getColleges(): ResponseEntity<List<CollegeDetails>> {
        val colleges = accessService.getColleges()
        return ResponseEntity.ok(colleges)
    }

    @GetMapping("/signup/degrees")
    fun getDegrees(): ResponseEntity<List<DegreeDetails>> {
        val degrees = accessService.getDegrees()
        return ResponseEntity.ok(degrees)
    }

    @GetMapping("/signup/interests")
    fun getInterests(): ResponseEntity<List<InterestDetails>> {
        val interests = accessService.getInterests()
        return ResponseEntity.ok(interests)
    }
}
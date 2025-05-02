package org.example.backendoportuniabravo.controller

import org.example.backendoportuniabravo.service.CompanyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/companies")
class CompanyController (private val service: CompanyService) {


    @GetMapping("{id}")
    @ResponseBody
    fun getCompanyById(@PathVariable id : Long) = service.findById(id)




}
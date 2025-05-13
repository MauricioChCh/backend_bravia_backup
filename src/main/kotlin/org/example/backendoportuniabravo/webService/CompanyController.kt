package org.example.backendoportuniabravo.webService


//@RestController
//@RequestMapping("\${url.companies}")
//class CompanyController (private val service: CompanyService) {
//
//    @GetMapping("{id}")
//    @ResponseBody
//    fun getCompanyById(@PathVariable id : Long) = service.findById(id)
//
//    @PostMapping
//    fun createCompany(@RequestBody company: CompanyUserInput) : ResponseEntity<CompanyUserResult>? {
//        val saved = service.create(company)
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
//    }
//
//    @PatchMapping("{id}/name")
//    fun updateCompanyName(@PathVariable id: Long, @RequestBody companyName: CompanyNameUpdate) : ResponseEntity<CompanyNameResult>? {
//        val updated = service.updateName(id, companyName)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//    @PatchMapping("{id}/description")
//    fun updateCompanyDescription(@PathVariable id: Long, @RequestBody companyDescription: CompanyDescriptionUpdate) : ResponseEntity<CompanyDescriptionResult>? {
//        val updated = service.updateDescription(id, companyDescription)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//    @PatchMapping("{id}/tags")
//    fun updateCompanyTags(@PathVariable id: Long, @RequestBody companyTags: CompanyTagsUpdate) : ResponseEntity<CompanyTagsResult>? {
//        val updated = service.updateTags(id, companyTags)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//    @PatchMapping("{id}/business_areas")
//    fun updateCompanyBusinessArea(@PathVariable id: Long, @RequestBody companyBusinessArea: CompanyBusinessAreaUpdate) : ResponseEntity<CompanyBusinessAreaResult>? {
//        val updated = service.updateBusinessArea(id, companyBusinessArea)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//    @PostMapping("{id}/contacts")
//    fun addCompanyContact(@PathVariable id: Long, @RequestBody contactInput: ContactInput) : ResponseEntity<CompanyContactsResult>? {
//        val updated = service.addContact(id, contactInput)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
////    @PostMapping("{id}/location")
////    fun addCompanyLocation(@PathVariable id: Long, @RequestBody locationInput: LocationInput) : ResponseEntity<LocationResult>? {
////        val updated = service.addLocation(id, locationInput)
////        return ResponseEntity.status(HttpStatus.OK).body(updated)
////    } // TODO: Creo que no se ocupa
//
//    @PatchMapping("{id}/location")
//    fun updateCompanyLocation(@PathVariable id: Long, @RequestBody locationUpdate: LocationUpdate) : ResponseEntity<LocationResult>? {
//        val updated = service.updateLocation(id, locationUpdate)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//    @DeleteMapping("{id}/contacts/{contactId}")
//    fun deleteCompanyContact(@PathVariable id: Long, @PathVariable contactId: Long) : ResponseEntity<CompanyContactsResult>? {
//        val updated = service.deleteContact(id, contactId)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//    @PatchMapping("{id}/profileImage")
//    fun updateCompanyImage(@PathVariable id: Long, @RequestBody companyImage: CompanyImageUpdate) : ResponseEntity<CompanyImageResult>? {
//        val updated = service.updateProfileImage(id, companyImage)
//        return ResponseEntity.status(HttpStatus.OK).body(updated)
//    }
//
//
//    @DeleteMapping("{id}")
//    @ResponseBody
//    fun deleteCompany(@PathVariable id: Long) {
//        service.deleteCompany(id)
//        ResponseEntity.status(HttpStatus.NO_CONTENT)
//    }
//}
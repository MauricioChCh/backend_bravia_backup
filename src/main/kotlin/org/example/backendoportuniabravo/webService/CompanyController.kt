package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("\${url.companies}")
class CompanyController (private val service: CompanyService) {

    @GetMapping("{id}")
    @ResponseBody
    fun getCompanyById(@PathVariable id : Long) = service.findById(id)



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

    @PatchMapping("{id}/tags")
    fun updateCompanyTags(@PathVariable id: Long, @RequestBody companyTags: CompanyTagsUpdate) : ResponseEntity<CompanyTagsResult>? {
        val updated = service.updateTags(id, companyTags)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @PatchMapping("{id}/business_areas")
    fun updateCompanyBusinessArea(@PathVariable id: Long, @RequestBody companyBusinessArea: CompanyBusinessAreaUpdate) : ResponseEntity<CompanyBusinessAreaResult>? {
        val updated = service.updateBusinessArea(id, companyBusinessArea)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @PostMapping("{id}/contacts")
    fun addCompanyContact(@PathVariable id: Long, @RequestBody contactInput: ContactInput) : ResponseEntity<CompanyContactsResult>? {
        val updated = service.addContact(id, contactInput)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @PatchMapping("{id}/location")
    fun updateCompanyLocation(@PathVariable id: Long, @RequestBody locationUpdate: LocationUpdate) : ResponseEntity<LocationResult>? {
        val updated = service.updateLocation(id, locationUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @DeleteMapping("{id}/contacts/{contactId}")
    fun deleteCompanyContact(@PathVariable id: Long, @PathVariable contactId: Long) : ResponseEntity<CompanyContactsResult>? {
        val updated = service.deleteContact(id, contactId)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @PatchMapping("{id}/profileImage")
    fun updateCompanyImage(@PathVariable id: Long, @RequestBody companyImage: CompanyImageUpdate) : ResponseEntity<CompanyImageResult>? {
        val updated = service.updateProfileImage(id, companyImage)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteCompany(@PathVariable id: Long) {
        service.deleteCompany(id)
        ResponseEntity.status(HttpStatus.NO_CONTENT)
    }

    @GetMapping("{id}/locations")
    fun getCompanyLocations(@PathVariable id: Long) : ResponseEntity<List<LocationDetails>>? {
        val updated = service.getLocations(id)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @GetMapping("{id}/internships")
    fun getCompanyInternships(@PathVariable id: Long) : ResponseEntity<List<InternshipResponseDTO>>? {
        val internships = service.getInternships(id)
        return ResponseEntity.status(HttpStatus.OK).body(internships)
    }

}
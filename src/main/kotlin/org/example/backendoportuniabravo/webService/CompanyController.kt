package org.example.backendoportuniabravo.webService

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("\${url.companies}")
class CompanyController (private val service: CompanyService) {

    @GetMapping("{username}")
    @ResponseBody
    fun getCompanyById(@PathVariable username : String) = service.findById(username)



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

    @GetMapping("{username}/locations")
    fun getCompanyLocations(@PathVariable username: String) : ResponseEntity<List<LocationDetails>>? {
        val updated = service.getLocations(username)
        return ResponseEntity.status(HttpStatus.OK).body(updated)
    }

    @GetMapping("{username}/internships")
    fun getCompanyInternships(@PathVariable username: String) : ResponseEntity<List<InternshipResponse>>? {
        val internships = service.getInternships(username)
        return ResponseEntity.status(HttpStatus.OK).body(internships)
    }

    @GetMapping("{username}/internships/{internshipId}")
    fun getCompanyInternship(@PathVariable username: String, @PathVariable internshipId: Long) : ResponseEntity<InternshipResponse> {
        val internship = service.getInternship(username, internshipId)
        return ResponseEntity.status(HttpStatus.OK).body(internship)
    }

    @GetMapping("internships/modalities")
    fun getAllModalities(): ResponseEntity<List<ModalityResponse>> {
        val modalities = service.getAllModalities()
        return ResponseEntity.status(HttpStatus.OK).body(modalities)
    }


    @PatchMapping("{username}/internships")
    fun updateInternship(@PathVariable("username") username: String, @RequestBody dto: InternshipRequestUpdateDTO): ResponseEntity<InternshipResponse> {
        val internship = service.updateInternship(username, dto)
        return ResponseEntity.status(HttpStatus.OK).body(internship)
    }

    @GetMapping("cities")
    fun getAllCities(): ResponseEntity<List<CityDetails>> {
        val cities = service.getAllCities()
        return ResponseEntity.status(HttpStatus.OK).body(cities)
    }

    @GetMapping("countries")
    fun getAllCountries(): ResponseEntity<List<CountryDetails>> {
        val countries = service.getAllCountries()
        return ResponseEntity.status(HttpStatus.OK).body(countries)
    }

    @GetMapping("tags")
    fun getAllTags(): ResponseEntity<List<TagDetails>> {
        val tags = service.getAllTags()
        return ResponseEntity.status(HttpStatus.OK).body(tags)
    }

}
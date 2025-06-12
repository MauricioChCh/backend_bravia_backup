package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException
import kotlin.Throws

/**
 * Service class for managing companies.
 * This class provides methods to create, update, and delete companies,
 * as well as to manage their contacts, locations, and other related entities.
 */
interface CompanyService {

    /**
     * Creates a new company based on the provided input.
     * @param companyUserInput The input data for creating a new company.
     * @return The result of the creation process.
     */
    fun create(companyUserInput: CompanyUserInput): CompanyUserResult?

//    fun update(companyUserInput: CompanyUserUpdate): CompanyUserResponse?

    /**
     * Updates the name of an existing company.
     * @param id The ID of the company to update.
     * @param companyName The new name for the company.
     * @return The result of the update process.
     */
    fun updateName(id: Long, companyName: CompanyNameUpdate): CompanyNameResult?

    /**
     * Updates the description of an existing company.
     * @param id The ID of the company to update.
     * @param companyDescription The new description for the company.
     * @return The result of the update process.
     */
    fun updateDescription(id: Long, companyDescription: CompanyDescriptionUpdate): CompanyDescriptionResult?

    /**
     * Updates the tags associated with an existing company.
     * @param id The ID of the company to update.
     * @param companyTags The new tags for the company.
     * @return The result of the update process.
     */
    fun updateTags(id: Long, companyTags: CompanyTagsUpdate): CompanyTagsResult?

    /**
     * Updates the business areas associated with an existing company.
     * @param id The ID of the company to update.
     * @param companyBusinessArea The new business areas for the company.
     * @return The result of the update process.
     */
    fun updateBusinessArea(id: Long, companyBusinessArea: CompanyBusinessAreaUpdate): CompanyBusinessAreaResult?

    /**
     * Updates the contacts associated with an existing company.
     * @param id The ID of the company to update.
     * @param companyContacts The new contacts for the company.
     * @return The result of the update process.
     */
    fun addContact(id: Long, contactInput: ContactInput): CompanyContactsResult?

    /**
     * Updates the location associated with an existing company.
     * @param id The ID of the company to update.
     * @param locationInput The new location for the company.
     * @return The result of the update process.
     */
    fun deleteContact(id: Long, contactId: Long): CompanyContactsResult?

    /**
     * Updates the location associated with an existing company.
     * @param id The ID of the company to update.
     * @param locationInput The new location for the company.
     * @return The result of the update process.
     */
    fun addLocation(id: Long, locationInput: LocationInput): LocationResult?

    /**
     * Updates the location associated with an existing company.
     * @param id The ID of the company to update.
     * @param locationUpdate The new location for the company.
     * @return The result of the update process.
     */
    fun updateLocation(id: Long, locationUpdate: LocationUpdate): LocationResult?

    /**
     * Updates the profile image of an existing company.
     * @param id The ID of the company to update.
     * @param image The new profile image for the company.
     * @return The result of the update process.
     */
    fun updateProfileImage(id: Long, image: CompanyImageUpdate): CompanyImageResult?

    /**
     * Updates the profile of an existing company.
     * @param id The ID of the company to delete.
     */
    fun deleteCompany(id: Long)

    /**
     * Finds all companies.
     * @return A list of all companies.
     * @throws NoSuchElementException if no companies are found.
     * @return A list of all companies.
     */
    fun findById(username: String): CompanyUserResponse?

    /**
     * Finds all company locations.
     * @return A list of all company locations.
     * @throws NoSuchElementException if no companies are found.
     */
    fun getLocations(username: String): List<LocationDetails>

    /**
     * Finds all internships associated with a company.
     * @return A list of all internships for the company.
     * @throws NoSuchElementException if no internships are found.
     */
    fun getInternships(username: String): List<InternshipResponse>


    /**
     * Retrieves a specific internship by its ID for a given company.
     * @param companyId The ID of the company.
     * @param internshipId The ID of the internship.
     * @return The internship response DTO if found, null otherwise.
     */
    fun getInternship(username: String, internshipId: Long): InternshipResponse?


    fun getAllModalities(): List<ModalityResponse>

    fun updateInternship(username: String, dto: InternshipRequestUpdateDTO): InternshipResponse?

}


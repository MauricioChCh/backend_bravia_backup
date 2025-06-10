package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.mapper.InternshipMapper
import org.example.backendoportuniabravo.mapper.LocationMapper
import org.example.backendoportuniabravo.repository.*
import org.example.backendoportuniabravo.repository.MarkedInternshipRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException
//import org.example.backendoportuniabravo.config.JwtSecurityConfiguration.passwordEncoder

@Service
class CompanyServiceImpl(
    @Autowired
    private val companyRepository: CompanyRepository,
    @Autowired
    private val companyMapper: CompanyMapper,
    @Autowired
    private val locationMapper: LocationMapper,
    @Autowired
    private val internshipMapper: InternshipMapper,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val profileRepository: ProfileRepository,
    @Autowired
    private val businessAreaRepository: BusinessAreaRepository,
    @Autowired
    private val tagRepository: TagRepository,
    @Autowired
    private val contactRepository: ContactRepository,
    private val locationRepository: LocationRepository,
    private val countryRepository: CountryRepository,
    private val cityRepository: CityRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val roleRepository: RoleRepository,
    private val markedInternshipRepository: MarkedInternshipRepository,
) : CompanyService {

    /**
     * Creates a new company based on the provided input.
     * @param companyUserInput The input data for creating a new company.
     * @return The result of the creation process, including the created company details.
     */
    override fun create(companyUserInput: CompanyUserInput): CompanyUserResult? {
        // Validate input
        val userInput = companyUserInput.user ?: throw IllegalArgumentException("User input cannot be null")

        // Check if the user already exists
        if (userRepository.existsByEmail(userInput.email!!)) {
            throw IllegalArgumentException("User with email '${userInput.email}' already exists")
        }

        userInput.password ?:
            throw IllegalArgumentException("User password cannot be null")

        val role = roleRepository.findByName("ROLE_COMPANY")
            .orElseThrow { NoSuchElementException("Role not found") }

        // Create and persist the User entity
        val user = User(
            firstName = userInput.firstName ?: throw IllegalArgumentException("User name cannot be null"),
            lastName = userInput.lastName ?: throw IllegalArgumentException("User last name cannot be null"),
            email = userInput.email ?: throw IllegalArgumentException("User email cannot be null"),
            password = passwordEncoder.encode(userInput.password),
            createDate = Date(),
            tokenExpired = false,
            enabled = true,
            roleList = mutableSetOf(role),
        )


        val businessArea = companyUserInput.businessArea?.id?.let {
            businessAreaRepository.findById(it).orElseThrow {
                IllegalArgumentException("Business area with id $it not found")
            }
        } ?: throw IllegalArgumentException("Business area is required")

        // Create profile, associate with User and persist it

        val profile = Profile(user = user, verified = false)
        user.profile = profile
        userRepository.save(user)
        val savedProfile = profileRepository.save(profile)

        // Create and persist the Company
        val company = Company(
            profile = savedProfile,
            name = companyUserInput.name ?: throw IllegalArgumentException("Company name is required"),
            businessAreas = mutableSetOf(businessArea),
            description = "",
            location = null
        )
        savedProfile.company = company // Back-reference for consistency

        // Persist the company
        val savedCompany = companyRepository.save(company)


        return companyMapper.companyToCompanyUserResult(savedCompany)
    }

    /**
     * Updates the name of an existing company.
     * @param id The ID of the company to update.
     * @param companyName The new name for the company.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateName(id: Long, companyName: CompanyNameUpdate): CompanyNameResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        company.name = companyName.name.toString()
        val saved = companyRepository.save(company)

        return companyMapper.companyToCompanyNameResult(saved)
    }

    /**
     * Updates the description of an existing company.
     * @param id The ID of the company to update.
     * @param companyDescription The new description for the company.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateDescription(id: Long, companyDescription: CompanyDescriptionUpdate): CompanyDescriptionResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        company.description = companyDescription.description.toString()
        val saved = companyRepository.save(company)

        return companyMapper.companyToCompanyDescriptionResult(saved)
    }

    /**
     * Updates the tags associated with an existing company.
     * @param id The ID of the company to update.
     * @param companyTags The new tags for the company.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateTags(id: Long, companyTags: CompanyTagsUpdate): CompanyTagsResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        val tags = companyTags.tags?.map { tagInput ->
            tagRepository.findById(tagInput.id!!)
                .orElseThrow { NoSuchElementException("Tag with id ${tagInput.id} not found") }
        } ?: throw IllegalArgumentException("Tags are required")

        // Limpiar relaciones existentes
        company.tags.forEach { it.companies.remove(company) }
        company.tags.clear()

        // Actualizar relaciones bidireccionales
        tags.forEach { tag ->
            tag.companies.add(company)
        }
        company.tags = tags.toMutableSet()

        // Guardar cambios
        tagRepository.saveAll(tags) // Asegura que los cambios en los tags se persistan
        val savedCompany = companyRepository.save(company)

        return companyMapper.companyToCompanyTagsResult(savedCompany)
    }

    /**
     * Updates the business areas associated with an existing company.
     * @param id The ID of the company to update.
     * @param companyBusinessArea The new business areas for the company.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateBusinessArea(id: Long, companyBusinessArea: CompanyBusinessAreaUpdate): CompanyBusinessAreaResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        val businessAreas = companyBusinessArea.businessAreas?.map { businessAreaInput ->
            businessAreaRepository.findById(businessAreaInput.id!!)
                .orElseThrow { NoSuchElementException("Business area with id ${businessAreaInput.id} not found") }
        } ?: throw IllegalArgumentException("Business areas are required")

        company.businessAreas.forEach { it.companies.remove(company) }
        company.businessAreas.clear()

        businessAreas.forEach { businessArea ->
            businessArea.companies.add(company)
        }
        company.businessAreas = businessAreas.toMutableSet()

        // Guardar cambios
        businessAreaRepository.saveAll(businessAreas) // Asegura que los cambios en los tags se persistan
        val savedCompany = companyRepository.save(company)

        return companyMapper.companyToCompanyBusinessAreaResult(savedCompany)
    }

    /**
     * Adds a new contact to an existing company.
     * @param id The ID of the company to update.
     * @param contactInput The input data for the new contact.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun addContact(id: Long, contactInput: ContactInput): CompanyContactsResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        val contact = companyMapper.companyContactInputToContact(contactInput)

        contact.company = company
        contactRepository.save(contact)

        company.contacts.add(contact)
        return companyMapper.companyToCompanyContactsResult(companyRepository.save(company))

    }

    /**
     * Deletes a contact from an existing company.
     * @param id The ID of the company to update.
     * @param contactId The ID of the contact to delete.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun deleteContact(id: Long, contactId: Long): CompanyContactsResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        val contact = contactRepository.findById(contactId)
            .orElseThrow { NoSuchElementException("Contact with id $contactId not found") }

        company.contacts.remove(contact)
        contactRepository.delete(contact)

        return companyMapper.companyToCompanyContactsResult(companyRepository.save(company))
    }

    /**
     * Adds a new location to an existing company.
     * @param id The ID of the company to update.
     * @param locationInput The input data for the new location.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class)
    override fun addLocation(id: Long, locationInput: LocationInput): LocationResult? {
        var location: Location? = null
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        if(company.location != null) { // TODO: Check if company just can have one location
            throw IllegalArgumentException("Company already has a location")
        }

        val country = locationInput.country?.id?.let { countryRepository.findById(it) }
            ?: throw IllegalArgumentException("Country is required")
        val city = locationInput.city?.id?.let { cityRepository.findById(it) }
            ?: throw IllegalArgumentException("Province is required")

        if (!country.isEmpty || !city.isEmpty) {
            location = Location(
                address = locationInput.address ?: throw IllegalArgumentException("Address is required"),
                country = country.get(),
                city = city.get()
            )
        }

        location?.company = company
        company.location = location
        val saved = companyRepository.save(company)

        return companyMapper.companyToLocationResult(saved)
    }

    /**
     * Updates the location of an existing company.
     * @param id The ID of the company to update.
     * @param locationUpdate The new location data for the company.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateLocation(id: Long, locationUpdate: LocationUpdate): LocationResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        val country = locationUpdate.country?.id?.let { countryRepository.findById(it).orElseThrow { IllegalArgumentException("Country not found") } }
        val city = locationUpdate.city?.id?.let { cityRepository.findById(it).orElseThrow { IllegalArgumentException("City not found") } }

        val location = Location(
            address = locationUpdate.address ?: throw IllegalArgumentException("Address is required"),
            country = country!!,
            city = city!!
        )

        // Save the new Location to ensure a unique ID
        val savedLocation = locationRepository.save(location)

        // Assign the new Location to the Company
        company.location = savedLocation
        val savedCompany = companyRepository.save(company)

        return companyMapper.companyToLocationResult(savedCompany)
    }

    /**
     * Updates the profile image of an existing company.
     * @param id The ID of the company to update.
     * @param image The new profile image data for the company.
     * @return The result of the update process, including the updated company details.
     */
    @Transactional
    @Throws(NoSuchElementException::class)
    override fun updateProfileImage(id: Long, image: CompanyImageUpdate): CompanyImageResult? {
        val company = companyRepository.findById(id).orElseThrow() {
            NoSuchElementException("Company with id $id not found")
        }

        company.imageUrl = image.imageUrl.toString()
        val saved = companyRepository.save(company)
        return companyMapper.companyToCompanyImageResult(saved)
    }

    /**
     * Deletes a company and its associated profile.
     * @param id The ID of the company to delete.
     * @throws NoSuchElementException if the company or profile is not found.
     */
    @Transactional
    @Throws(NoSuchElementException::class)
    override fun deleteCompany(id: Long) {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        val profile = company.profile ?: throw NoSuchElementException("Profile not found")

        val user = profile.user
        user?.profile = null

        profileRepository.delete(profile)
    }

    /**
     * Finds a company by its ID.
     * @param id The ID of the company to find.
     * @return The found company, or null if not found.
     * @throws NoSuchElementException if the company is not found.
     */
    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): CompanyUserResponse? {
        val company: Optional<Company> = companyRepository.findById(id)
        if (company.isEmpty) {
            throw NoSuchElementException("Company with id $id not found")
        }
        return companyMapper.companyToCompanyUserResponse(
            company.get()
        )
    }

    /**
     * Retrieves all company locations.
     * @return A list of all locations associate with company.
     * @throws NoSuchElementException if no companies are found.
     */
    @Throws(NoSuchElementException::class)
    override fun getLocations(id: Long): List<LocationDetails> {
        val company: Optional<Company> = companyRepository.findById(id)
        if (company.isEmpty) {
            throw NoSuchElementException("Company with id $id not found")
        }
        val locations = company.get().location?.let { listOf(it) } ?: emptyList()
        return locations.map { locationMapper.locationToLocationDetails(it) }
    }

    /**
     * Retrieves all internships associated with a company.
     * @return A list of all internships for the company.
     * @throws NoSuchElementException if no internships are found.
     */
    @Throws(NoSuchElementException::class)
    override fun getInternships(id: Long): List<InternshipResponseDTO> {
        val company: Optional<Company> = companyRepository.findById(id)
        if (company.isEmpty) {
            throw NoSuchElementException("Company with id $id not found")
        }
        val internships = isInternshipsMark(company.get().profile?.user?.id!!, company.get().internships)

        return internships.map { internshipMapper.internshipToInternshipResponseDTO(it) }
    }

    override fun getInternship(userId: Long, internshipId: Long): InternshipResponseDTO? {
        val company: Optional<Company> = companyRepository.findById(userId)
        if (company.isEmpty) {
            throw NoSuchElementException("Company with id $userId not found")
        }
        val internship = isInternshipMark(company.get().profile?.user?.id!!, company.get().internships.find { it.id == internshipId }!! )


        return internshipMapper.internshipToInternshipResponseDTO(internship!!)
    }



    /**
     * Marks internships as bookmarked for a user.
     * @param userId The ID of the user.
     * @param internships The list of internships to check for bookmarks.
     * @return A list of internships with their bookmarked status updated.
     */
    private fun isInternshipsMark(userId: Long, internships: List<Internship>?): List<Internship> {
        val markedInternships = markedInternshipRepository.findByUserId(userId)

        // If the internships list is not null or empty, update the bookmarked status
        if (!internships.isNullOrEmpty()) {
            internships.forEach { internship ->
                internship.bookmarked = markedInternships.any { it.internship.id == internship.id }
            }
            return internships
        }

        // If internships is null or empty, return an empty list
        return emptyList()
    }

    private fun isInternshipMark(userId: Long, internship: Internship ): Internship? {
        val markedInternships = markedInternshipRepository.findByUserId(userId)

        // Check if the internship is bookmarked
        internship.bookmarked = markedInternships.any { it.internship.id == internship.id }

        return internship
    }


}
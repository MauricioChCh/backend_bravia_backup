package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.*
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import kotlin.Throws

interface CompanyService {
    fun create(companyUserInput: CompanyUserInput): CompanyUserResult?
//    fun update(companyUserInput: CompanyUserUpdate): CompanyUserResponse?
    fun updateName(id: Long, companyName: CompanyNameUpdate): CompanyNameResult?
    fun updateDescription(id: Long, companyDescription: CompanyDescriptionUpdate): CompanyDescriptionResult?
    fun updateTags(id: Long, companyTags: CompanyTagsUpdate): CompanyTagsResult?
    fun updateBusinessArea(id: Long, companyBusinessArea: CompanyBusinessAreaUpdate): CompanyBusinessAreaResult?
    fun addContact(id: Long, contactInput: ContactInput): CompanyContactsResult?
    fun deleteContact(id: Long, contactId: Long): CompanyContactsResult?
    fun addLocation(id: Long, locationInput: LocationInput): LocationResult?
    fun updateLocation(id: Long, locationUpdate: LocationUpdate): LocationResult?
    fun deleteById(id: Long)
    fun findById(id: Long): CompanyUserResponse?
}

@Service
class CompanyServiceImpl(
    @Autowired
    private val companyRepository: CompanyRepository,
    @Autowired
    private val companyMapper: CompanyMapper,
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
    private val cityRepository: CityRepository

    ) : CompanyService {

    override fun create(companyUserInput: CompanyUserInput): CompanyUserResult? {
        // Validate input
        val userInput = companyUserInput.user ?: throw IllegalArgumentException("User input cannot be null")

        // Check if the user already exists
        if (userRepository.existsByEmail(userInput.email!!)) {
            throw IllegalArgumentException("User with email '${userInput.email}' already exists")
        }

        // Create and persist the User entity
        val user = User(
            firstName = userInput.firstName ?: throw IllegalArgumentException("User name cannot be null"),
            lastName = userInput.lastName ?: throw IllegalArgumentException("User last name cannot be null"),
            email = userInput.email ?: throw IllegalArgumentException("User email cannot be null"),
            password = userInput.password ?: throw IllegalArgumentException("User password cannot be null"),
            createDate = java.util.Date(),
            tokenExpired = false,
            enabled = true
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

    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateName(id: Long, companyName: CompanyNameUpdate): CompanyNameResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        company.name = companyName.name.toString()
        val saved = companyRepository.save(company)

        return companyMapper.companyToCompanyNameResult(saved)
    }

    @Transactional
    @Throws(NoSuchElementException::class, IllegalArgumentException::class)
    override fun updateDescription(id: Long, companyDescription: CompanyDescriptionUpdate): CompanyDescriptionResult? {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        company.description = companyDescription.description.toString()
        val saved = companyRepository.save(company)

        return companyMapper.companyToCompanyDescriptionResult(saved)
    }

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
        company.tags = tags.toMutableSet() as MutableSet<Tag>

        // Guardar cambios
        tagRepository.saveAll(tags) // Asegura que los cambios en los tags se persistan
        val savedCompany = companyRepository.save(company)

        return companyMapper.companyToCompanyTagsResult(savedCompany)
    }

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


    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        val company = companyRepository.findById(id)
            .orElseThrow { NoSuchElementException("Company with id $id not found") }

        companyRepository.deleteById(company.id!!)
    }

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
}
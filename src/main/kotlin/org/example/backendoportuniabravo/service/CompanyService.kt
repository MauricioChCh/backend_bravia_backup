package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.*
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.Tag
import org.example.backendoportuniabravo.entity.User
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import kotlin.jvm.Throws

interface CompanyService {
    fun create(companyUserInput: CompanyUserInput): CompanyUserResult?
//    fun update(companyUserInput: CompanyUserUpdate): CompanyUserResponse?
    fun updateName(id: Long, companyName: CompanyNameUpdate): CompanyNameResult?
    fun updateDescription(id: Long, companyDescription: CompanyDescriptionUpdate): CompanyDescriptionResult?
    fun updateTags(id: Long, companyTags: CompanyTagsUpdate): CompanyTagsResult?
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
            firstName = userInput.firstname ?: throw IllegalArgumentException("User name cannot be null"),
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
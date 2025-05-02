package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.CompanyUserInput
import org.example.backendoportuniabravo.dto.CompanyUserResponse
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.dto.CompanyUserUpdate
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.User
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.repository.BusinessAreaRepository
import org.example.backendoportuniabravo.repository.CompanyRepository
import org.example.backendoportuniabravo.repository.ProfileRepository
import org.example.backendoportuniabravo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.jvm.Throws

interface CompanyService {
    fun create(companyUserInput: CompanyUserInput): CompanyUserResult?
    fun update(companyUserInput: CompanyUserUpdate): CompanyUserResponse?
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
    private val bussinessAreaRepository: BusinessAreaRepository
) : CompanyService {

    override fun create(input: CompanyUserInput): CompanyUserResult? {
        val userInput = input.user ?: throw IllegalArgumentException("User input cannot be null")

        val user = userRepository.save(
            User(
                firstName = userInput.name ?: throw IllegalArgumentException("User name cannot be null"),
                lastName = userInput.lastName ?: throw IllegalArgumentException("User last name cannot be null"),
                email = userInput.email ?: throw IllegalArgumentException("User email cannot be null"),
                password = userInput.password ?: throw IllegalArgumentException("User password cannot be null"),
                createDate = java.util.Date(),
                tokenExpired = false,
                enabled = true
            )
        )

        val profile = profileRepository.save(Profile(user = user, verified = false))

        val businessArea = input.businessArea ?.let {
            bussinessAreaRepository.findById(it.id ?: throw IllegalArgumentException("Business area id cannot be null"))
                .orElseThrow {throw IllegalArgumentException("Business area not found with ID: ${it.id}}")}
        } ?: throw IllegalArgumentException("Business area must be provided")

        val company = Company(
            profile = profile,
            name = input.name ?: throw IllegalArgumentException("Company name is required"),
            businessAreas = mutableSetOf(businessArea),
            description = "",
            location = null
        )

        return companyMapper.companyToCompanyUserResult(companyRepository.save(company))
    }

    @Throws(NoSuchElementException::class)
    override fun update(companyUserInput: CompanyUserUpdate): CompanyUserResponse? {
        val companyUser = companyMapper.companyUserUpdateToCompany(companyUserInput)
        return companyMapper.companyToCompanyUserResponse(
            companyRepository.save(companyUser)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        if(!companyRepository.findById(id).isEmpty) {
            companyRepository.deleteById(id)
        } else {
            throw NoSuchElementException("Company with id $id not found")
        }
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
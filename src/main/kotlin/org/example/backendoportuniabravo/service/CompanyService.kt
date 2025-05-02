package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.CompanyUserInput
import org.example.backendoportuniabravo.dto.CompanyUserResponse
import org.example.backendoportuniabravo.dto.CompanyUserResult
import org.example.backendoportuniabravo.dto.CompanyUserUpdate
import org.example.backendoportuniabravo.entity.Company
import org.example.backendoportuniabravo.mapper.CompanyMapper
import org.example.backendoportuniabravo.repository.CompanyRepository
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
    private val companyMapper: CompanyMapper
) : CompanyService {

    override fun create(companyUserInput: CompanyUserInput): CompanyUserResult? {
        val companyUser = companyMapper.companyUserInputToCompany(companyUserInput)
        return companyMapper.companyToCompanyUserResult(
            companyRepository.save(companyUser)
        )
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
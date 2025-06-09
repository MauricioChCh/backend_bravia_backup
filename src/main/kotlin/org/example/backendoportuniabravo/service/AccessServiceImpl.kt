package org.example.backendoportuniabravo.service

import org.springframework.stereotype.Service
import org.example.backendoportuniabravo.dto.BusinessAreaDetails
import org.example.backendoportuniabravo.dto.CollegeDetails
import org.example.backendoportuniabravo.dto.DegreeDetails
import org.example.backendoportuniabravo.repository.BusinessAreaRepository
import org.example.backendoportuniabravo.repository.CollegeRepository
import org.example.backendoportuniabravo.repository.DegreeRepository
import org.springframework.beans.factory.annotation.Autowired

@Service
class AccessServiceImpl(
    @Autowired
    private val businessAreaRepository: BusinessAreaRepository,
    private val collegeRepository: CollegeRepository,
    private val degreeRepository: DegreeRepository,
) : AccessService {

    override fun getBusinessAreas(): List<BusinessAreaDetails> {
        return businessAreaRepository.findAll().map { BusinessAreaDetails(it.id, it.name) }
    }

    override fun getColleges(): List<CollegeDetails> {
        return collegeRepository.findAll().map { CollegeDetails(it.id, it.name) }
    }

    override fun getDegrees(): List<DegreeDetails> {
        return degreeRepository.findAll().map { DegreeDetails(it.id, it.name) }
    }
}
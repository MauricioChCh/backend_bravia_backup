package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.dto.BusinessAreaDetails
import org.example.backendoportuniabravo.dto.CollegeDetails
import org.example.backendoportuniabravo.dto.DegreeDetails
import org.example.backendoportuniabravo.dto.InterestDetails

interface AccessService {

    fun getBusinessAreas(): List<BusinessAreaDetails>
    fun getColleges(): List<CollegeDetails>
    fun getDegrees(): List<DegreeDetails>
    fun getInterests(): List<InterestDetails>

}
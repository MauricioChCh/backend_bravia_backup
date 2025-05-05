package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long>

@Repository
interface CityRepository : JpaRepository<City, Long>

@Repository
interface LocationRepository : JpaRepository<Location, Long>



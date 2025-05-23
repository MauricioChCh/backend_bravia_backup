package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
//    fun existsByName(name: String): Boolean
    fun findByName(name: String): Country?
}

@Repository
interface CityRepository : JpaRepository<City, Long> {
    fun findByName(name: String): City?
}

@Repository
interface LocationRepository : JpaRepository<Location, Long>



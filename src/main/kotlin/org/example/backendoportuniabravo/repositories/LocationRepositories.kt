package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long>

@Repository
interface ProvinceRepository : JpaRepository<Province, Long>

@Repository
interface LocationRepository : JpaRepository<Location, Long>



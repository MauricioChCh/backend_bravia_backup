package org.example.backendoportuniabravo.repositories

import jakarta.persistence.*
import org.example.backendoportuniabravo.entities.Hobbie
import org.example.backendoportuniabravo.entities.Students
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository : JpaRepository<Students, Long>

@Repository
interface HobbieRepository : JpaRepository<Hobbie, Long>

@Repository
interface CertificationRepository : JpaRepository<Hobbie, Long>

@Repository
interface ExperienceRepository : JpaRepository<Hobbie, Long>

@Repository
interface SkillRepository : JpaRepository<Hobbie, Long>

@Repository
interface CareerRepository : JpaRepository<Hobbie, Long>

@Repository
interface CVUrlRepository : JpaRepository<Hobbie, Long>

@Repository
interface MockInterviewRepository : JpaRepository<Hobbie, Long>

@Repository
interface DegreeRepository : JpaRepository<Hobbie, Long>

@Repository
interface LanguageRepository : JpaRepository<Hobbie, Long>

@Repository
interface CollegeRepository : JpaRepository<Hobbie, Long>

@Repository
interface InterestRepository : JpaRepository<Hobbie, Long>





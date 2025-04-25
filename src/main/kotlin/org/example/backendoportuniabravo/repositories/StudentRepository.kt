package org.example.backendoportuniabravo.repositories

import jakarta.persistence.*
import org.example.backendoportuniabravo.entities.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository : JpaRepository<Student, Long>

@Repository
interface HobbieRepository : JpaRepository<Hobbie, Long>

@Repository
interface CertificationRepository : JpaRepository<Certification, Long>

@Repository
interface ExperienceRepository : JpaRepository<Experience, Long>

@Repository
interface SkillRepository : JpaRepository<Skill, Long>

@Repository
interface CareerRepository : JpaRepository<Career, Long>

@Repository
interface CVUrlRepository : JpaRepository<CVUrl, Long>

@Repository
interface MockInterviewRepository : JpaRepository<MockInterview, Long>

@Repository
interface DegreeRepository : JpaRepository<Degree, Long>

@Repository
interface LanguageRepository : JpaRepository<Language, Long>

@Repository
interface CollegeRepository : JpaRepository<College, Long>

@Repository
interface InterestRepository : JpaRepository<Interest, Long>




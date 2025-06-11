package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Long>{
    fun findByProfileId(userId: Long): Student?
}

@Repository
interface HobbieRepository : JpaRepository<Hobby, Long> {
    fun findByStudent(student: Student): List<Hobby>
}

@Repository
interface CertificationRepository : JpaRepository<Certification, Long> {
    fun findByStudent(student: Student): List<Certification>
}

@Repository
interface ExperienceRepository : JpaRepository<Experience, Long> {
    fun findByStudent(student: Student): List<Experience>
}

@Repository
interface SkillRepository : JpaRepository<Skill, Long> {
    fun findByStudent(student: Student): List<Skill>
}

@Repository
interface CareerRepository : JpaRepository<Career, Long> {
    fun findByStudent(student: Student): List<Career>
}

@Repository
interface CVUrlRepository : JpaRepository<CVUrl, Long> {
    fun findByStudent(student: Student): List<CVUrl>
}

@Repository
interface MockInterviewRepository : JpaRepository<MockInterview, Long> {
    fun findByStudent(student: Student): List<MockInterview>
}

@Repository
interface DegreeRepository : JpaRepository<Degree, Long> {
    fun findByStudentsContaining (student: MutableSet<Student>): List<Degree>?
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Degree?
}

@Repository
interface LanguageRepository : JpaRepository<Language, Long> {
    fun findByStudentsContaining(student: MutableSet<Student>): List<Language>
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Language?
}

@Repository
interface CollegeRepository : JpaRepository<College, Long> {
    fun findByStudentsContaining(student: Student): List<College>?
    fun existsByName(name: String): Boolean
    fun findByName(name: String): College?
}

@Repository
interface InterestRepository : JpaRepository<Interest, Long> {
    fun findByStudentsContaining(student: MutableSet<Student>): List<Interest>?
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Interest?
}




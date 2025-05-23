package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.Privilege
import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.Role
import org.example.backendoportuniabravo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByIdAndEnabledTrue(id: Long): User?
    fun findByProfile(profile: Profile): User?
    fun existsByEmail(email: String): Boolean
    fun deleteByProfile(profile: Profile)
}

@Repository
interface ProfileRepository : JpaRepository<Profile, Long>

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(@Param("name") name: String): Optional<Role>
}

@Repository
interface PrivilegeRepository : JpaRepository<Privilege, Long> {
    fun findByName(@Param("name") name: String): Optional<Privilege>
}
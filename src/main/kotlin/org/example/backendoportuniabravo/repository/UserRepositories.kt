package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.Privilege
import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.Role
import org.example.backendoportuniabravo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
//    fun findByIdAndEnabledTrue(id: Long): User? // TODO: Probablemente eliminar
//    fun findByProfile(profile: Profile): User? // TODO: Probablemente eliminar
    fun existsByEmail(email: String): Boolean
//    fun deleteByProfile(profile: Profile) // TODO: Probablemente eliminar
    fun deleteByProfile(profile: Profile?)
    fun findByProfileId(profileId: Long?): User?
    fun existsByProfileId(profileId: Long): Boolean
    fun findByEmailIgnoreCase(email: String): Optional<User>
}

@Repository
interface ProfileRepository : JpaRepository<Profile, Long>{
    fun save(profile: Profile): Profile

}

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(@Param("name") name: String): Optional<Role>
}

@Repository
interface PrivilegeRepository : JpaRepository<Privilege, Long>{
    fun findByName(@Param("name") name: String): Optional<Privilege>
}
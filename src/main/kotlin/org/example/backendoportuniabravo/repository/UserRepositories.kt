package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByIdAndEnabledTrue(id: Long): User?
    fun findByProfile(profile: Profile): User?
    fun existsByEmail(email: String): Boolean
    fun deleteByProfile(profile: Profile)
    fun findByProfileId(profileId: Long?): User?
    fun existsByProfileId(profileId: Long): Boolean
    fun findByEmailIgnoreCase(email: String): Optional<User>
}

@Repository
interface ProfileRepository : JpaRepository<Profile, Long>{
    fun save(profile: Profile): Profile

}

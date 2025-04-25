package org.example.backendoportuniabravo.repositories

import org.example.backendoportuniabravo.entities.Profile
import org.example.backendoportuniabravo.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
@Repository
interface ProfileRepository : JpaRepository<Profile, Long>

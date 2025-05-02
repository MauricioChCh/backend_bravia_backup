package org.example.backendoportuniabravo.repository

import org.example.backendoportuniabravo.entity.Profile
import org.example.backendoportuniabravo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>

@Repository
interface ProfileRepository : JpaRepository<Profile, Long>

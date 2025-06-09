package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.entity.User

interface UserService {
    fun findById(id: Long): User
    fun findByEmail(email: String): User?
    fun findByProfileId(profileId: Long?): User?
    fun existsByEmail(email: String): Boolean
    fun existsByProfileId(profileId: Long): Boolean
    fun save(user: User): User
    fun deleteByProfileId(profileId: Long)
}
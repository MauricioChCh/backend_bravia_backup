package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.entity.User
import org.example.backendoportuniabravo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with ID $id not found") }
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun findByProfileId(profileId: Long?): User? {
        return userRepository.findByProfileId(profileId)
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    override fun existsByProfileId(profileId: Long): Boolean {
        return userRepository.existsByProfileId(profileId)
    }

    @Transactional
    override fun save(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    override fun deleteByProfileId(profileId: Long) {
        val user = userRepository.findByProfileId(profileId)
            ?: throw NoSuchElementException("User with Profile ID $profileId not found")
        userRepository.deleteByProfile(user.profile)
    }
}
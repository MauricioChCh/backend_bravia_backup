package org.example.backendoportuniabravo.service

import org.example.backendoportuniabravo.entity.Role
import org.example.backendoportuniabravo.entity.User
import org.example.backendoportuniabravo.repository.RoleRepository
import org.example.backendoportuniabravo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AppUserDetailsService (
    @Autowired
    val userRepository: UserRepository? = null,
    @Autowired
    val roleRepository: RoleRepository? = null
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userAuth: org.springframework.security.core.userdetails.User
        val user: User = userRepository?.findByEmail(username)
            ?: return org.springframework.security.core.userdetails.User(
                "", "", true, true, true, true,
                getAuthorities(
                    listOf(
                        roleRepository?.findByName("ROLE_USER")?.get()
                    )
                )
            )

        userAuth = org.springframework.security.core.userdetails.User(
            user.email, user.password, user.enabled, true, true,
            true, getAuthorities(user.roleList.toMutableList())
        )

        return userAuth
    }

    private fun getAuthorities(roles: List<Role?>): Collection<GrantedAuthority> {
        return roles.flatMap { role ->
            sequenceOf(SimpleGrantedAuthority(role!!.name)) +
                    role.privilegeList.map { privilege -> SimpleGrantedAuthority(privilege.name) }
        }.toList()
    }

}
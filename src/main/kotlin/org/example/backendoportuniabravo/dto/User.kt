package org.example.backendoportuniabravo.dto

import java.util.*


data class UserInput(
    var firstname: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
)

data class UserResult(
    var id: Long? = null,
    var email: String? = null,
    var firstname: String? = null,
    var lastName: String? = null,
)


data class UserUpdate(
    var id: Long? = null,
    var firstname: String? = null,
    var lastName: String? = null,
    var email: String? = null,
)

data class UserDetails(
    var id: Long? = null,
    var firstname: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var createDate: Date? = null,
)

data class ProfileDetails(
    var id: Long? = null,
    var user: UserDetails? = null,
    var verified: Boolean? = null,
)
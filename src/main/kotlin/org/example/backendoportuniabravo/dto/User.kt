package org.example.backendoportuniabravo.dto


data class UserInput(
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
)

data class UserResult(
    var id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    var lastName: String? = null,
)


data class UserUpdate(
    var id: Long? = null,
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
)
package sru.edu.sru_lib_management.auth.domain.model

data class User(
    val userId: Long?,
    var username: String,
    var password: String,
    var roles: Role
)

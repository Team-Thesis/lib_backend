package sru.edu.sru_lib_management.auth.domain.model

data class User(
    val userId: Long?,
    val username: String,
    val password: String,
    val roles: Role
)
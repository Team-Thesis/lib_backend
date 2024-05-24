package sru.edu.sru_lib_management.auth.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @NotBlank
    @Email
    val username: String,
    val password: String
)
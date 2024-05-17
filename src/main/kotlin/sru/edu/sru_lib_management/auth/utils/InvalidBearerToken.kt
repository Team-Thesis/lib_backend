package sru.edu.sru_lib_management.auth.utils

import org.springframework.security.core.AuthenticationException

class InvalidBearerToken(message: String?): AuthenticationException(message)
package sru.edu.sru_lib_management.auth.domain.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.auth.data.repository.AuthRepository
import sru.edu.sru_lib_management.auth.domain.dao.AuthRequest
import sru.edu.sru_lib_management.auth.domain.dao.AuthResponse
import sru.edu.sru_lib_management.auth.domain.model.Role
import sru.edu.sru_lib_management.auth.domain.model.User
import sru.edu.sru_lib_management.auth.jwt.JwtTokenSupport
import sru.edu.sru_lib_management.auth.utils.AuthResult


@Service
class AuthService(
    private val userDetailsService: ReactiveUserDetailsService,
    private val repository: AuthRepository,
    private val jwtSupport: JwtTokenSupport,
    private val encoder: PasswordEncoder
){
    suspend fun register(request: AuthRequest): AuthResult<String>{
        return runCatching {
            val encryptPassword = encoder.encode(request.password)
            val newUser = User(
                userId = null,
                username = request.username,
                password = encryptPassword,
                roles = Role.USER
            )
            repository.save(newUser)
            "Success!"
        }.fold(
            onSuccess = {
                AuthResult.Success(it)
            },
            onFailure = {
                AuthResult.Failure("${it.message}")
            }
        )
    }

    suspend fun login(request: AuthRequest): AuthResult<String>{
        return runCatching {
            val user = userDetailsService.findByUsername(request.username).awaitSingleOrNull()
            if (user == null)
                AuthResult.InputError("Invalid username or password.")
            val token = user?.let {
                if (encoder.matches(request.password, user.password))
                    jwtSupport.generateToken(request.username).value
                else null
            }
            token!!
        }.fold(
            onSuccess = {
                AuthResult.Success(it)
            },
            onFailure = {
                AuthResult.Failure("${it.message}")
            }
        )
    }

}

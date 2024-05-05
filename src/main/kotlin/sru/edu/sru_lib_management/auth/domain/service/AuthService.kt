package sru.edu.sru_lib_management.auth.domain.service

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.auth.dao.AuthRequest
import sru.edu.sru_lib_management.auth.dao.AuthResponse
import sru.edu.sru_lib_management.auth.data.repository.AuthRepositoryImp
import sru.edu.sru_lib_management.auth.domain.model.Role
import sru.edu.sru_lib_management.auth.domain.model.User
import sru.edu.sru_lib_management.auth.domain.model.UserDetailImp

@Service
class AuthService(
    private val repository: AuthRepositoryImp,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    suspend fun register(request: AuthRequest): Boolean {
        try {
            if (repository.findUserByUsername(request.username).isPresent) {
                return false
            }
            val user = User(
                null,
                request.username,
                passwordEncoder.encode(request.password),
                Role.ROLE_USER
            )
            repository.save(user)
            return true
        } catch (e: Exception) {
            AuthResponse("false register")
            return false
        }
    }

    suspend fun authenticate(request: AuthRequest): AuthResponse {
        return try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username,
                    request.password
                )
            )
            val user = repository.findUserByUsername(request.username).orElseThrow()
            val token = jwtService.generateToken(UserDetailImp(user))
            AuthResponse(token)
        }catch (e: Exception){
            AuthResponse(e.message + HttpStatus.INTERNAL_SERVER_ERROR.toString())
        }
    }
}

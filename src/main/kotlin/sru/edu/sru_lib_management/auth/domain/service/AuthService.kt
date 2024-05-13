package sru.edu.sru_lib_management.auth.domain.service

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.auth.data.repository.AuthRepository
import sru.edu.sru_lib_management.auth.domain.dao.AuthRequest
import sru.edu.sru_lib_management.auth.domain.dao.AuthResponse
import sru.edu.sru_lib_management.auth.domain.model.Role
import sru.edu.sru_lib_management.auth.domain.model.User
import sru.edu.sru_lib_management.auth.domain.model.UserDetailImp


@Service
class AuthService(
    private val repository: AuthRepository
){

    @Autowired private val jwtService: JwtService? = null
    @Autowired private val authenticationManager: AuthenticationManager? = null
    @Autowired private val passwordEncoder: PasswordEncoder? = null

    suspend fun register(request: AuthRequest): String {
        try{
            val user = User(
                null,
                request.username,
                passwordEncoder!!.encode(request.password),
                Role.ROLE_USER
            )
            repository.save(user)
            return "Success!"
        }catch (e: Exception){
            return "${e.message}"
        }
    }

    fun authenticate(request: AuthRequest): AuthResponse {

       authenticationManager!!.authenticate(
           UsernamePasswordAuthenticationToken(
               request.username,
               request.password
           )
       )
       val user =  runBlocking { repository.findUserByUsername(request.username) ?: throw UsernameNotFoundException("Username not found.") }
       val token = jwtService!!.generateToken(UserDetailImp(user))
       /// return token
       return AuthResponse(token)

    }
}

package sru.edu.sru_lib_management.auth.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.auth.data.repository.AuthRepository
import sru.edu.sru_lib_management.auth.domain.dao.AuthRequest
import sru.edu.sru_lib_management.auth.domain.dao.AuthResponse
import sru.edu.sru_lib_management.auth.domain.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val repository: AuthRepository,
    private val service: AuthService
) {

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @GetMapping("/hello")
    fun role(): ResponseEntity<String> {
        return ResponseEntity.ok("Register or authenticate please")
    }

    @PostMapping("/register")
    suspend fun register(
        @RequestBody request: AuthRequest
    ): ResponseEntity<String> {
        // Check all fields and password length
        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordTooShort = request.password.length < 8

        if (areFieldsBlank || isPasswordTooShort)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Fields can not be blank and Password can not less than 8")
        if (repository.findUserByUsername(request.username) != null)
            return ResponseEntity.badRequest().body("Username already exist.")
        /// Save User
        return ResponseEntity(service.register(request), HttpStatus.CREATED)
    }

    @PostMapping("/authenticate")
    suspend fun authenticate(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse>{

        // Check user null or not if its null
        val user = repository.findUserByUsername(request.username)
             ?: return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthResponse("Username not found. Register first."))
        // if password is not null check password match or not

        val isPasswordMatch = passwordEncoder!!.matches(request.password, user.password)

        println(isPasswordMatch)

        if (!isPasswordMatch)
            return ResponseEntity.badRequest().build()

        return ResponseEntity(service.authenticate(request), HttpStatus.OK)
    }
}

package sru.edu.sru_lib_management.auth.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.auth.dao.AuthRequest
import sru.edu.sru_lib_management.auth.dao.AuthResponse
import sru.edu.sru_lib_management.auth.data.repository.AuthRepositoryImp
import sru.edu.sru_lib_management.auth.domain.service.AuthService
import sru.edu.sru_lib_management.core.util.SaveCallBack

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    @Autowired private val service: AuthService? = null
    private val passwordEncoder: PasswordEncoder? = null
    private val repository: AuthRepositoryImp? = null

    @GetMapping("/hello")
    fun role(): ResponseEntity<String> {
        return ResponseEntity.ok("Register or authenticate please")
    }

    @PostMapping("/register")
    suspend fun register(@RequestBody request: AuthRequest, callBack: SaveCallBack): ResponseEntity<AuthResponse> {
        // Check all fields and password length
        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordTooShort = request.password.length < 8
        if (areFieldsBlank || isPasswordTooShort)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthResponse("Fields can not be blank and Password can not less than 8"))
        val isSuccess = service!!.register(request, callBack)
        if (!isSuccess)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthResponse("Username already exists"))
        return ResponseEntity.ok(AuthResponse("Success!"))

    }

    @PostMapping("/authenticate")
    suspend fun authenticate(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        /// Check user null or not if its null
        val user = repository!!.findUserByUsername(request.username).orElseThrow()
        // if password is not null check password match or not
        val isPasswordMatch = passwordEncoder!!.matches(request.password, user.password)
        if (!isPasswordMatch)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AuthResponse("Incorrect password."))
        // if everything perfect authenticate user and return token
        return ResponseEntity.ok(service!!.authenticate(request))
    }
}

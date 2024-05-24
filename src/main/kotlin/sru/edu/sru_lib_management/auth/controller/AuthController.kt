package sru.edu.sru_lib_management.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.auth.data.repository.AuthRepository
import sru.edu.sru_lib_management.auth.domain.dto.AuthRequest
import sru.edu.sru_lib_management.auth.domain.dto.AuthResponse
import sru.edu.sru_lib_management.auth.domain.service.AuthService
import sru.edu.sru_lib_management.auth.domain.service.EmailValidationService
import sru.edu.sru_lib_management.auth.utils.AuthResult

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val service: AuthService,
    private val repository: AuthRepository,
    private val validationService: EmailValidationService
){

    @PostMapping("/register")
    suspend fun register(
        @RequestBody request: AuthRequest
    ): ResponseEntity<String>{
        val areFieldBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordTooShort = request.password.length < 8
        // check field
        if (areFieldBlank && isPasswordTooShort)
            return ResponseEntity.badRequest().body("Field cannot be blank, password must be greater than 8.")

        // Check username or email valid or not
        // Check if the email format and domain are valid
        val emailValidationError = validationService.validateEmail(request.username)
        if (emailValidationError != null) {
            return ResponseEntity.badRequest().body(emailValidationError)
        }


        // check username is already exist or not
        val user = repository.findUserByUsername(request.username)
        if (user != null)
            return ResponseEntity.badRequest().body("User already exist")

        return when(val result = service.register(request)){
            is AuthResult.Success -> ResponseEntity(result.data, HttpStatus.CREATED)
            is AuthResult.InputError -> ResponseEntity.badRequest().body(result.inputErrMsg)
            is AuthResult.Failure -> ResponseEntity.internalServerError().body(result.errorMsg)
        }
    }

    @PostMapping("/login")
    suspend fun login(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse>{
        repository.findUserByUsername(request.username)
            ?: return ResponseEntity.badRequest().body(AuthResponse("Incorrect username."))
        return when(val result = service.login(request)){
            is AuthResult.Success -> ResponseEntity(AuthResponse(result.data), HttpStatus.OK)
            is AuthResult.InputError -> ResponseEntity(AuthResponse(result.inputErrMsg), HttpStatus.BAD_REQUEST)
            is AuthResult.Failure -> ResponseEntity(AuthResponse(result.errorMsg), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}

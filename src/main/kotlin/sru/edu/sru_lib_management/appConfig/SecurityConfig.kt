package sru.edu.sru_lib_management.appConfig


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import sru.edu.sru_lib_management.auth.config.JwtAuthFilter
import sru.edu.sru_lib_management.auth.data.repository.AuthRepository
import sru.edu.sru_lib_management.auth.domain.model.UserDetailImp

@Suppress("removal", "DEPRECATION")
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
class SecurityConfig (
    private val authRepository: AuthRepository,
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailServices(): UserDetailsService {
        return UserDetailsService { username ->
            UserDetailImp(
                authRepository.findUserByUsername(username) ?: throw UsernameNotFoundException("User not found")
                )
        }
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        configuration: AuthenticationConfiguration
    ): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailServices())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{
        return http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers(
                    "api/v1/auth/**",
                    "api/v1/student/**"
                ).permitAll()
            .and()
            .authorizeHttpRequests()
                .requestMatchers(
                    "api/v1/dashboard/**",
                    "api/v1/secret/**"
                ).authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

}

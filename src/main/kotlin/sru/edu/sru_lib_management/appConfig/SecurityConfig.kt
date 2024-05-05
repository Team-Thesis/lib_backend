package sru.edu.sru_lib_management.appConfig


import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import sru.edu.sru_lib_management.auth.config.JwtAuthFilter
import sru.edu.sru_lib_management.auth.domain.model.UserDetailImp
import sru.edu.sru_lib_management.auth.domain.repository.AuthRepository

@Suppress("removal", "DEPRECATION")
@EnableWebSecurity
@Configuration
class SecurityConfig (
    @Qualifier("authRepositoryImp") private val authRepository: AuthRepository
) {
    @Autowired private lateinit var jwtAuthFilter: JwtAuthFilter

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            runBlocking {
                UserDetailImp(authRepository.findUserByUsername(username).orElseThrow {
                    UsernameNotFoundException("User not found.")
                })
            }
        }
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    suspend fun authenticationProvider(): AuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userDetailsService())
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        return daoAuthenticationProvider
    }

    @Bean
    suspend fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(
                "api/v1/auth/**",
                "api/v1/student/**",
                "api/v1/dashboard/**")
            .permitAll()
            .and()
            .authorizeHttpRequests()
            .requestMatchers("api/v1/sru/**").authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}

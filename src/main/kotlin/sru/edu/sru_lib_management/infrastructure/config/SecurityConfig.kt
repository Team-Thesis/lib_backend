package sru.edu.sru_lib_management.infrastructure.config


import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono
import sru.edu.sru_lib_management.auth.data.repository.AuthRepository
import sru.edu.sru_lib_management.auth.domain.jwt.JwtAuthenticationConverter
import sru.edu.sru_lib_management.auth.domain.jwt.JwtAuthenticationManager


@Configuration
@EnableWebFluxSecurity
class SecurityConfig (
    private val repository: AuthRepository
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailServices(encoder: PasswordEncoder): ReactiveUserDetailsService {
        return ReactiveUserDetailsService { username ->
            mono {
                val users = repository.findUserByUsername(username)
                users?.let {
                    User.withUsername(it.username)
                        .password(it.password)
                        .roles(it.roles.name)
                        .build()
                }
            }
        }
    }


    @Bean
    @Suppress("removal", "DEPRECATION")
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        converter: JwtAuthenticationConverter,
        authManager: JwtAuthenticationManager
    ): SecurityWebFilterChain {

        val filter = AuthenticationWebFilter(authManager)
        filter.setServerAuthenticationConverter(converter)

        http
            .exceptionHandling()
            .authenticationEntryPoint { exchange, _ ->
                Mono.fromRunnable {
                    exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    exchange.response.headers.set(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                }
            }
            .and()
            .authorizeExchange()
            .pathMatchers(
                "/api/v1/auth/register",
                "/api/v1/auth/login",
                "/api/v1/**"
            ).permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()

        return http.build()
    }

}
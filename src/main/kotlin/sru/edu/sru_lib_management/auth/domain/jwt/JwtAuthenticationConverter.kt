package sru.edu.sru_lib_management.auth.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst("Authorization"))
            .filter {request ->
                request.startsWith("Bearer ")
            }
            .map { token ->
                token.substring(7)
            }.map { tokenValue ->
                BearerToken(tokenValue)
            }
    }
}
package sru.edu.sru_lib_management.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class AllowAccess : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
        registry.addMapping("/**")
            .allowedOrigins("https://react-js-inky-three.vercel.app")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
    }
}
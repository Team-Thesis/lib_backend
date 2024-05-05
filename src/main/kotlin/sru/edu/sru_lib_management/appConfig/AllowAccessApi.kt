package sru.edu.sru_lib_management.appConfig

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AllowAccessApi : WebMvcConfigurer{
    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
        registry.addMapping("/api/**")
            .allowedOrigins("url_client")
            .allowedMethods("PUT", "GET", "DELETE", "POST")
            .allowedHeaders("*")
    }
}
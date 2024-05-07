package sru.edu.sru_lib_management.appConfig
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AllowAccessAPI: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
            registry.addMapping("api/**")
                .allowedHeaders("*")
                .allowedOrigins("url_react")
                .allowedMethods("PUT", "POST", "DELETE", "GET")
    }
}

package sru.edu.sru_lib_management.appConfig

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class DatabaseConfig : AbstractR2dbcConfiguration(){

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val host = "localhost"
        val port = 3306
        val database = "sru_library"
        val username = "root"
        val password = "viwath@261102"

        return MySqlConnectionFactory.from(
            MySqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .database(database)
                .username(username)
                .password(password)
                .build()
        )
    }

}

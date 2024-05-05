package sru.edu.sru_lib_management.appConfig

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory
import io.asyncer.r2dbc.mysql.MySqlConnectionFactoryProvider
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.DatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class DatabaseConfig : AbstractR2dbcConfiguration(){

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val host = "localhost"
        val port = 3306
        val database = "sru_library"
        val username = "root"
        val password = "Lo01@reak111102"

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

//    @Bean
//    fun dataBaseClient(connectionFactory: ConnectionFactory): DatabaseClient{
//        return DatabaseClient.create(connectionFactory)
//    }

//    @Bean
//    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer{
//        val initializer = ConnectionFactoryInitializer()
//        initializer.setConnectionFactory(connectionFactory)
//        val populator = ResourceDatabasePopulator(ClassPathResource("classpath:schema.sql"))
//        initializer.setDatabasePopulator()
//        return initializer
//    }
}

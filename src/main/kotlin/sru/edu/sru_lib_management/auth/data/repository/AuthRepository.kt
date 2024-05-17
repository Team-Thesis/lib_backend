package sru.edu.sru_lib_management.auth.data.repository

import kotlinx.coroutines.runBlocking
import org.springframework.r2dbc.core.*
import sru.edu.sru_lib_management.auth.domain.model.User
import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.auth.domain.model.Role
import sru.edu.sru_lib_management.auth.domain.repository.IAuthRepository

@Repository
class AuthRepository(
    private val client: DatabaseClient
) : IAuthRepository<User>{

    override suspend fun save(entity: User) {
        client.sql(SAVE_USER_QUERY)
            .bindValues(paramMap(entity))
            .await()
    }

    override suspend fun update(entity: User) {
        client.sql(UPDATE_USER_QUERY)
            .bindValues(paramMap(entity))
            .fetch()
            .awaitRowsUpdated()
    }

    override suspend fun findUserByUsername(username: String): User? {
        return runBlocking {
            client.sql(FIND_USER_BY_USERNAME)
                .bind("username", username)
                .map { row ->
                    User(
                        row.get("user_id", Long::class.java)!!,
                        row.get("username", String::class.java)!!,
                        row.get("password", String::class.java)!!,
                        row.get("roles", Role::class.java)!!
                    )
                }.awaitSingleOrNull()
        }
    }

    private fun paramMap(user: User): Map<String, Any> = mapOf(
        "username" to user.username,
        "password" to user.password,
        "roles" to user.roles
    )


    companion object{
        private const val SAVE_USER_QUERY = "Insert Into users(username, password, roles) VALUES (:username, :password, :roles);"
        private const val UPDATE_USER_QUERY = "Update users SET password = :password, roles = :roles Where username = :username"
        private const val FIND_USER_BY_USERNAME = "SELECT * from users WHERE username = :username"
    }

}
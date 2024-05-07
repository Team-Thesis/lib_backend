package sru.edu.sru_lib_management.auth.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import sru.edu.sru_lib_management.auth.domain.model.Role
import sru.edu.sru_lib_management.auth.domain.model.User
import sru.edu.sru_lib_management.auth.domain.repository.AuthRepository
import java.util.*

@Component
class AuthRepositoryImp(
    private val client: DatabaseClient
) : AuthRepository {

    override suspend fun findUserByUsername(username: String): Optional<User> = coroutineScope {
        val deferred = async {
            client.sql(FIND_USER_BY_USERNAME)
                .bind("username", username)
                .map { row ->
                    User(
                        row.get("user_id", Long::class.java)!!,
                        row.get("username", String::class.java)!!,
                        row.get("password", String::class.java)!!,
                        row.get("roles", Role::class.java)!!
                    )
                }.one().awaitFirstOrNull()
        }
        Optional.ofNullable(deferred.await())
    }


    override suspend fun save(data: User) {
        client.sql(SAVE_USER_QUERY)
            .bindValues(paramMap(user = data))
            .await()

    }

    @Transactional
    override suspend fun update(data: User) {
        client.sql(UPDATE_USER_QUERY)
            .bindValues(paramMap(data))
            .fetch()
            .awaitRowsUpdated()
    }

    override suspend fun getById(id: Long): User? {
        TODO("Not yet implemented")
    }

    private fun paramMap(user: User): Map<String, Any> = mapOf(
        "userId" to user.userId!!,
        "username" to user.username,
        "password" to user.password,
        "roles" to user.roles
    )

    companion object{
        private const val SAVE_USER_QUERY = "Insert Into users(username, password, roles) " +
                "VALUES (:username, :password, :roles);"
        private const val UPDATE_USER_QUERY = "Update users SET password = :password, roles = :roles Where username = :username"
        private const val FIND_USER_BY_USERNAME = "SELECT * from users WHERE username = :username"
    }
}

package sru.edu.sru_lib_management.auth.data.repository

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import sru.edu.sru_lib_management.auth.domain.model.User
import sru.edu.sru_lib_management.auth.domain.repository.AuthRepository
import java.util.*

@Component
class AuthRepositoryImp(
    private val client: DatabaseClient
) : AuthRepository {

    override suspend fun findUserByUsername(username: String): Optional<User> {
        TODO()
    }

    @Transactional
    override suspend fun save(data: User) {
        client.sql(SAVE_USER_QUERY)
            .bindValues(paramMap(user = data))
            .await()

    }

    override suspend fun update(data: User): User {
        TODO("Not yet implemented")
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
    }
}

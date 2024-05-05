package sru.edu.sru_lib_management.auth.domain.repository

import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.auth.domain.model.User
import java.util.Optional

@Repository
interface AuthRepository : IAuthCrudRepository<User, Long> {
    suspend fun findUserByUsername(username: String): Optional<User>
}
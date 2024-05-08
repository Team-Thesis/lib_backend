package sru.edu.sru_lib_management.auth.domain.repository
import org.springframework.stereotype.Component

@Component
interface IAuthCrudRepository<T, ID> {
    suspend fun save(data: T)
    suspend fun update(data: T)
    suspend fun getById(id: ID): T?
}
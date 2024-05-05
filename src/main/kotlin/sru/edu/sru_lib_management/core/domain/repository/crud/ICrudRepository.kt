package sru.edu.sru_lib_management.core.domain.repository.crud

import kotlinx.coroutines.flow.Flow

interface ICrudRepository<T, Any> {
    suspend fun save(data: T)
    suspend fun update(data: T)
    suspend fun getById(id: Any): T?
    fun getAll(): Flow<T>
    suspend fun delete(id: Any): Boolean
}
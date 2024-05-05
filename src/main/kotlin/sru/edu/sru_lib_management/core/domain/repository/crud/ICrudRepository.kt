package sru.edu.sru_lib_management.core.domain.repository.crud

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.util.SaveCallBack

interface ICrudRepository<T, Any> {
    suspend fun save(data: T, callBack: SaveCallBack)
    suspend fun update(data: T): T
    suspend fun getById(id: Any): T?
    fun getAll(): Flow<T>
    suspend fun delete(id: Any): Boolean
}
package sru.edu.sru_lib_management.auth.domain.repository

import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.util.SaveCallBack

@Component
interface IAuthCrudRepository<T, ID> {
    suspend fun save(data: T, callBack: SaveCallBack)
    suspend fun update(data: T): T
    suspend fun getById(id: ID): T?
}
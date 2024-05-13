package sru.edu.sru_lib_management.auth.domain.repository

import sru.edu.sru_lib_management.auth.domain.model.User

interface IAuthRepository<T> {
    suspend fun save(entity: T)
    suspend fun update(entity: T)
    fun findUserByUsername(username: String): User?
}
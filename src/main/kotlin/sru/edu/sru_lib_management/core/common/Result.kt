package sru.edu.sru_lib_management.core.common

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Failure(val errorMsg: String): Result<Nothing>()
    data class ClientError(val clientErrMsg: String): Result<Nothing>()
}
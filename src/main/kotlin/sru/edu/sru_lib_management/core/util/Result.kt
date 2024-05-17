package sru.edu.sru_lib_management.core.util


sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Failure(val errorMsg: String): Result<Nothing>()
}
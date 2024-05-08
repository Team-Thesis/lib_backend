package sru.edu.sru_lib_management.core.util

import sru.edu.sru_lib_management.core.domain.model.Students

sealed class CUDResult {
    data class Success(val successMsg: Any): CUDResult()
    data class Failure(val errorMsg: String): CUDResult()
}
package sru.edu.sru_lib_management.core.util

interface CallBack {
    fun onSuccess()
    fun onFailure(error: String)
}
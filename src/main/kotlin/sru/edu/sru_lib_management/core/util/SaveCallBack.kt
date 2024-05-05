package sru.edu.sru_lib_management.core.util

interface SaveCallBack {
    fun onSuccess()
    fun onFailure(error: String)
}
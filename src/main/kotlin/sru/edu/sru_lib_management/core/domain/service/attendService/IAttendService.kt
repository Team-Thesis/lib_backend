package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.util.Result

interface IAttendService {
    suspend fun saveAttend(attend: Attend): Result<Attend?>
    suspend fun updateAttend(attend: Attend): Result<Attend?>
    suspend fun deleteAttend(attendID: Long): Result<Boolean>
    fun getAllAttend(): Result<Flow<Attend>>
    suspend fun getAttend(attendID: Long): Result<Attend?>
}
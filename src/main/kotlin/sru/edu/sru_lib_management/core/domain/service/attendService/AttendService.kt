package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.model.Students

interface AttendService {
    suspend fun saveAttend(attend: Attend): Attend?
    suspend fun updateAttend(attend: Attend): Attend?
    suspend fun deleteAttend(attendID: Long): Boolean
    fun getAllAttend(): Flow<Students>
    suspend fun getAttend(attendID: Long): Attend
}
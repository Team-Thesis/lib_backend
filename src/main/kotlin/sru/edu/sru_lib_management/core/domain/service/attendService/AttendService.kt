package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.util.CallBack

interface AttendService {
    suspend fun saveAttend(students: Students, callBack: CallBack)
    suspend fun updateAttend(students: Students, callBack: CallBack)
    suspend fun deleteAttend(studentID: Long): Boolean?
    fun getAllAttend(): Flow<Students>
    suspend fun getAttend(studentID: Long): Attend
}
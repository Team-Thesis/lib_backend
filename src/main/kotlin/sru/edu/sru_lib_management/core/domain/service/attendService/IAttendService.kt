package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.util.Result
import java.sql.Date
import java.sql.Time
import java.time.LocalDate

interface IAttendService {
    fun getAllAttend(): Result<Flow<Attend>>
    suspend fun saveAttend(attend: Attend): Result<Attend?>
    suspend fun updateAttend(attend: Attend): Result<Attend?>
    suspend fun deleteAttend(attendID: Long): Result<Boolean>
    suspend fun getCustomAttByDate(date: Int): Result<Flow<Attend>>
    suspend fun getAttend(attendID: Long): Result<Attend?>
    suspend fun getAttByStudentID(studentID: Long): Result<Attend?>
    suspend fun updateExitingTime(studentID: Long, date: LocalDate, exitingTime: Time): Result<Boolean>
    suspend fun countAttendCustomTime(date: Int): Result<Int?>
}
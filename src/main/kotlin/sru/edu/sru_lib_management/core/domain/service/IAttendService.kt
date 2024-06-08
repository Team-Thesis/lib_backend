package sru.edu.sru_lib_management.core.domain.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.AttendDto
import sru.edu.sru_lib_management.core.domain.dto.dashbord.CustomEntryCount
import java.time.LocalDate
import java.time.LocalTime

@Service
interface IAttendService {
    fun getAllAttend(): Result<Flow<Attend>>
    suspend fun saveAttend(attend: Attend): Result<Attend?>
    suspend fun updateAttend(attend: Attend): Result<Attend?>
    suspend fun deleteAttend(attendId: Long): Result<Boolean>
    suspend fun getCustomAttByDate(date: Int): Result<Flow<Attend>>
    suspend fun getAttend(attendId: Long): Result<Attend?>
    suspend fun getAttByStudentID(studentId: Long, date: LocalDate): Result<Attend?>
    suspend fun updateExitingTime(studentId: Long, date: LocalDate, exitingTimes: LocalTime): Result<Boolean>
    suspend fun countAttendCustomTime(date: Int): Result<Int?>

    suspend fun countVisitorsForPeriod(): Result<Map<LocalDate, Int>>
    suspend fun countAndCompareAttend(date: LocalDate, period: Int): Result<CustomEntryCount>

    suspend fun getAttendDetails(): Result<Flow<AttendDto>>
}

package sru.edu.sru_lib_management.core.domain.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.dashbord.CustomEntry
import java.time.LocalDate
import java.time.LocalTime

@Service
interface IAttendService {
    fun getAllAttend(): Result<Flow<Attend>>
    suspend fun saveAttend(attend: Attend): Result<Attend?>
    suspend fun updateAttend(attend: Attend): Result<Attend?>
    suspend fun deleteAttend(attendID: Long): Result<Boolean>
    suspend fun getCustomAttByDate(date: Int): Result<Flow<Attend>>
    suspend fun getAttend(attendID: Long): Result<Attend?>
    suspend fun getAttByStudentID(studentID: Long, date: LocalDate): Result<Attend?>
    suspend fun updateExitingTime(studentID: Long, date: LocalDate, exitingTimes: LocalTime): Result<Boolean>
    suspend fun countAttendCustomTime(date: Int): Result<Int?>

    suspend fun countVisitorsForPeriod(): Result<Map<LocalDate, Int>>
}

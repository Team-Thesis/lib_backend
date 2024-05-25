package sru.edu.sru_lib_management.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository
import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

@Repository
interface AttendRepository : ICrudRepository<Attend, Long> {
    fun getCustomAttend(date: Int): Flow<Attend>
    suspend fun updateExitingTime(exitingTimes: LocalTime, studentID: Long, date: LocalDate): Boolean
    suspend fun count(date: Int): Int?
    suspend fun getAttendByStudentID(studentID: Long, date: LocalDate): Attend?
}

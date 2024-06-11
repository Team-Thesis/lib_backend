package sru.edu.sru_lib_management.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.core.domain.dto.AttendDto
import sru.edu.sru_lib_management.core.domain.dto.CompareValue
import sru.edu.sru_lib_management.core.domain.dto.dashbord.DayVisitor
import sru.edu.sru_lib_management.core.domain.dto.dashbord.TotalMajorVisitor
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository
import java.time.LocalDate
import java.time.LocalTime

@Repository
interface AttendRepository : ICrudRepository<Attend, Long> {
    fun getCustomAttend(date: Int): Flow<Attend>
    suspend fun updateExitingTime(exitingTimes: LocalTime, studentId: Long, date: LocalDate): Boolean
    suspend fun count(date: Int): Int?
    suspend fun getAttendByStudentID(studentId: Long, date: LocalDate): Attend?
    suspend fun getWeeklyVisit(): List<DayVisitor>

    suspend fun countCurrentAndPreviousAttend(date: LocalDate, period: Int): CompareValue
    suspend fun getAttendDetail(): List<AttendDto>
    suspend fun totalMajorVisit(): TotalMajorVisitor
}

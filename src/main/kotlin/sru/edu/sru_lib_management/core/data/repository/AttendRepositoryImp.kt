package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.data.query.AttendQuery.DELETE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.GET_ALL_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.GET_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.GET_ATTEND_QUERY_BY_STUDENT_ID
import sru.edu.sru_lib_management.core.data.query.AttendQuery.SAVE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.UPDATE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.UPDATE_EXIT_TIME
import sru.edu.sru_lib_management.core.domain.dto.AttendDto
import sru.edu.sru_lib_management.core.domain.dto.CompareValue
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import java.time.LocalDate
import java.time.LocalTime

@Component
class AttendRepositoryImp(
    private val client: DatabaseClient
) : AttendRepository {

    override fun getCustomAttend(date: Int): Flow<Attend> = client
        .sql("Call GetAttendByCustomTime(:date)")
            .bind("date", date)
            .map { row: Row, _ ->
                row.mapToAttend()
            }
            .flow()

    override suspend fun save(entity: Attend): Attend {
        val attend = mapOf(
            "studentId" to entity.studentId,
            "entryTimes" to entity.entryTimes,
            "purpose" to entity.purpose,
            "date" to entity.date
        )
        client.sql(SAVE_ATTEND_QUERY)
            .bindValues(attend)
            .await()
        return entity
    }

    override suspend fun update(entity: Attend): Attend {
        client.sql(UPDATE_ATTEND_QUERY)
            .bindValues(paramMap(entity))
            .fetch()
            .awaitRowsUpdated()
        return entity
    }

    override suspend fun getById(id: Long): Attend? {
        return client.sql(GET_ATTEND_QUERY)
            .bind("attendID", id)
            .map { row: Row, _ ->
                row.mapToAttend()
            }.awaitSingleOrNull()
    }

    override fun getAll(): Flow<Attend> {
        return client.sql(GET_ALL_ATTEND_QUERY)
            .map { row: Row, _ ->
                row.mapToAttend()
            }.flow()
    }

    override suspend fun delete(id: Long): Boolean {
        val rowEffect = client.sql(DELETE_ATTEND_QUERY)
            .bind("attendID", id)
            .fetch()
            .awaitRowsUpdated()
        return rowEffect > 0
    }

    override suspend fun updateExitingTime(exitingTimes: LocalTime, studentId: Long, date: LocalDate): Boolean {
        val rowEffect = client.sql(UPDATE_EXIT_TIME)
            .bind("exitingTimes", exitingTimes)
            .bind("studentId", studentId)
            .bind("date", date)
            .fetch()
            .awaitRowsUpdated()
        return rowEffect > 0
    }

    override suspend fun count(date: Int): Int? {
        return client.sql("CALL CountAttendByCustomTime(:date)")
            .bind("date", date)
            .map {row ->
                row.get("attendance_count", Int::class.java)
            }
            .awaitSingle()
    }

    override suspend fun getAttendByStudentID(studentId: Long, date: LocalDate): Attend? {
        return client.sql(GET_ATTEND_QUERY_BY_STUDENT_ID)
            .bind("studentId", studentId)
            .bind("date", date)
            .map { row: Row, _ ->
                row.mapToAttend()
            }.awaitSingleOrNull()
    }

    override suspend fun countVisitorsForPeriod(): Map<LocalDate, Int> {
        return client.sql("CALL CountAttendPerWeek()")
            .map { row, _ ->
                val date = row.get("date", LocalDate::class.java)!!
                val count = row.get("count", Int::class.java)!!
                date to  count
            }
            .all()
            .collectList()
            .awaitSingle()
            .toMap()
    }

    override suspend fun countCurrentAndPreviousBorrow(date: LocalDate, period: Int): CompareValue {
        val param = mapOf(
            "date" to date,
            "period" to period
        )
        return client.sql("CALL CountAttendByPeriod(:date, :period)")
            .bindValues(param)
            .map {row ->
                CompareValue(
                    row.get("current_value", Int::class.java)!!,
                    row.get("previous_value", Int::class.java)!!
                )
            }
            .one()
            .awaitSingle()
    }

    override suspend fun getAttendDetail(): List<AttendDto> {
        return client.sql("CALL GetAttendDetails()")
            .map {row ->
                AttendDto(
                    studentId = row.get("student_id", Long::class.java)!!,
                    studentName = row.get("studentName", String::class.java)!!,
                    gender = row.get("gender", String::class.java)!!,
                    major = row.get("majorName", String::class.java)!!,
                    entryTimes = row.get("entryTimes", LocalTime::class.java)!!,
                    exitingTimes = row.get("exitingTime", LocalTime::class.java),
                    purpose = row.get("purpose", String::class.java)!!,
                    status = null
                )
            }
            .all()
            .collectList()
            .awaitSingle()
            .toList()
    }


    private fun paramMap(attend: Attend): Map<String, Any?> = mapOf(
        "attendID" to attend.attendId,
        "studentID" to attend.studentId,
        "entryTimes" to attend.entryTimes,
        "exitingTimes" to attend.exitingTimes,
        "purpose" to attend.purpose,
        "date" to attend.date
    )

    private fun Row.mapToAttend(): Attend = Attend(
        attendId = this.get("attend_id", Long::class.java)!!,
        studentId = this.get("student_id", Long::class.java)!!,
        entryTimes = this.get("entry_times", LocalTime::class.java)!!,
        exitingTimes = this.get("exiting_times", LocalTime::class.java),
        purpose = this.get("purpose", String::class.java)!!,
        date = this.get("date", LocalDate::class.java)!!,
    )
}
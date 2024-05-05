package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.data.query.AttendQuery.GET_ALL_ATTEND_QUERY
import org.springframework.r2dbc.core.DatabaseClient
import sru.edu.sru_lib_management.core.data.query.AttendQuery
import sru.edu.sru_lib_management.core.data.query.AttendQuery.GET_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.SAVE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.UPDATE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate

@Component
class AttendRepositoryImp(
    private val client: DatabaseClient
) : AttendRepository {

    override fun getCustomAttend(date: Date): Flow<Attend> = client
        .sql(GET_ATTEND_QUERY)
            .map { row ->
                rowAttendMap(row as Row)
            }
            .flow()


    override suspend fun save(data: Attend) {
        client.sql(SAVE_ATTEND_QUERY)
            .bindValues(paramMap(data))
            .await()
    }

    override suspend fun update(data: Attend) {
        client.sql(UPDATE_ATTEND_QUERY)
            .bindValues(paramMap(data))
            .fetch()
            .awaitRowsUpdated()
    }

    override suspend fun getById(id: Long): Attend? {
        return client.sql(GET_ATTEND_QUERY)
            .bind("attendID", id)
            .map { row ->
                rowAttendMap(row as Row)
            }
            .awaitSingle()
    }

    override fun getAll(): Flow<Attend> {
        return client.sql(GET_ALL_ATTEND_QUERY)
            .map { row ->
                rowAttendMap(row as Row)
            }
            .flow()
    }

    override suspend fun delete(id: Long): Boolean {
        val affectRow = client.sql(AttendQuery.DELETE_ATTEND_QUERY)
            .bind("attendID", id)
            .fetch()
            .awaitRowsUpdated()
        return affectRow > 0
    }


    private fun paramMap(attend: Attend): Map<String, Any> = mapOf(
        "attendID" to attend.attendID!!,
        "studentID" to attend.studentID,
        "entryTimes" to attend.entryTimes,
        "exitingTimes" to attend.exitingTimes,
        "date" to attend.date,
        "purpose" to attend.purpose
    )

    private fun rowAttendMap(row: Row): Attend = Attend(
        attendID = row.get("attend_id", Long::class.java)!!,
        studentID = row.get("student_id", Long::class.java)!!,
        entryTimes = row.get("entry_times", Timestamp::class.java)!!,
        exitingTimes = row.get("exiting_times", Timestamp::class.java)!!,
        date = row.get("date", LocalDate::class.java)!!,
        purpose = row.get("purpose", String::class.java)!!
    )
}

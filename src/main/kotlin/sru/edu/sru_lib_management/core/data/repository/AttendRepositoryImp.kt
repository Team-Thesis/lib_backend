package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.R2dbcRepository
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
            .map { row: Row, _ ->
                row.mapToAttend()
            }
            .flow()

    override suspend fun save(entity: Attend): Attend {
        client.sql(SAVE_ATTEND_QUERY)
            .bindValues(paramMap(entity))
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
            }.awaitSingle()
    }

    override fun getAll(): Flow<Attend> {
        return client.sql(GET_ALL_ATTEND_QUERY)
            .map { row: Row, _ ->
                row.mapToAttend()
            }.flow()
    }

    override suspend fun delete(id: Long) {
        client.sql(AttendQuery.DELETE_ATTEND_QUERY)
            .bind("attendID", id)
            .fetch()
            .awaitRowsUpdated()
    }


    private fun paramMap(attend: Attend): Map<String, Any> = mapOf(
        "attendID" to attend.attendID!!,
        "studentID" to attend.studentID,
        "entryTimes" to attend.entryTimes,
        "exitingTimes" to attend.exitingTimes,
        "date" to attend.date,
        "purpose" to attend.purpose
    )

    private fun Row.mapToAttend(): Attend = Attend(
        attendID = this.get("attend_id", Long::class.java)!!,
        studentID = this.get("student_id", Long::class.java)!!,
        entryTimes = this.get("entry_times", Timestamp::class.java)!!,
        exitingTimes = this.get("exiting_times", Timestamp::class.java)!!,
        date = this.get("date", LocalDate::class.java)!!,
        purpose = this.get("purpose", String::class.java)!!
    )
}
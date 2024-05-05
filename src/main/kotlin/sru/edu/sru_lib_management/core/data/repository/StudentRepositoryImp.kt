package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import sru.edu.sru_lib_management.core.data.query.StudentQuery.GET_STUDENTS_QUERY
import sru.edu.sru_lib_management.core.data.query.StudentQuery.GET_STUDENT_QUERY
import sru.edu.sru_lib_management.core.data.query.StudentQuery.SAVE_STUDENT_QUERY
import sru.edu.sru_lib_management.core.data.query.StudentQuery.UPDATE_STUDENT_QUERY
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.StudentRepository
import sru.edu.sru_lib_management.core.util.APIException
import sru.edu.sru_lib_management.core.util.CallBack
import java.time.LocalDate

@Component
class StudentRepositoryImp (
    private val client: DatabaseClient
): StudentRepository {
    /*
    * Override function
    * */
    @Transactional
    override suspend fun save(data: Students) {
        client.sql(SAVE_STUDENT_QUERY)
            .bindValues(paramMap(data))
            .await()
    }

    override suspend fun update(data: Students) {
        client.sql(UPDATE_STUDENT_QUERY)
            .bindValues(paramMap(data))
            .fetch()
            .awaitRowsUpdated()
    }

    override suspend fun getById(id: Long): Students? {
        return client.sql(GET_STUDENT_QUERY)
            .bind("studentID", id)
            .map { row ->
                mapToStudent(row as Row)
            }.awaitSingle()
    }

    override fun getAll(): Flow<Students>  {
        return client.sql(GET_STUDENTS_QUERY)
            .map { row ->
                mapToStudent(row as Row)
            }
            .flow()
    }

    override suspend fun delete(id: Long): Boolean {
        TODO()
    }

    private fun paramMap(students: Students): Map<String, Any> = mapOf(
        "studentID" to students.studentID!!,
        "studentName" to students.studentName,
        "gender" to  students.dateOfBirth,
        "dateOfBirth" to students.dateOfBirth,
        "degreeLevelID" to students.degreeLevelID,
        "majorID" to students.majorID,
        "generation" to students.generation
    )

    private fun mapToStudent(row: Row): Students = Students(
            studentID = row.get("student_id", Long::class.java),
            studentName = row.get("student_name", String::class.java)!!,
            gender = row.get("gender", String::class.java)!!,
            dateOfBirth = row.get("date_of_birth", LocalDate::class.java)!!,
            degreeLevelID = row.get("degree_level_id", Int::class.java)!!,
            majorID = row.get("major_id", Int::class.java)!!,
            generation = row.get("generation", Int::class.java)!!
    )
}

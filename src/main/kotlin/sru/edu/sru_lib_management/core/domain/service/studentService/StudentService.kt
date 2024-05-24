package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.StudentRepository
import sru.edu.sru_lib_management.core.util.Result

@Service
class StudentService(
   @Qualifier("studentRepositoryImp") private val repository: StudentRepository
) : IStudentService {

    override suspend fun saveStudent(students: Students): Result<Students?> {
        return runCatching {
            repository.save(students)
        }.fold(
            onSuccess = { student ->
                Result.Success(student)
            },
            onFailure = { e ->
                Result.Failure(e.message ?: "Unknown error occurred.")
            }
        )
    }

    override suspend fun updateStudent(studentID: Long, students: Students): Result<Students?> {
        return runCatching {
            val existingStudent = repository.getById(studentID)
                ?: return@runCatching Result.ClientError("Not found student with ID: $studentID")
            println(existingStudent)
            val updateStudents = existingStudent.copy(
                studentName = students.studentName,
                gender = students.gender,
                dateOfBirth = students.dateOfBirth,
                degreeLevelID = students.degreeLevelID,
                majorID = students.majorID,
                generation = students.generation
            )
            repository.update(updateStudents)
        }.fold(
            onSuccess = {
                Result.Success(students)
            },
            onFailure = {e ->
                Result.Failure(e.message ?: "Unknown error occurred.")
            }
        )
    }
    override suspend fun deleteStudent(studentID: Long): Result<Boolean> {
        return runCatching {
            repository.delete(studentID)
        }.fold(
            onSuccess = { Result.Success(true) },
            onFailure = { e ->
                Result.Failure(e.message ?: "Unknown error occurred.")
            }
        )
    }

    override fun getStudents(): Result<Flow<Students>> {
        return runCatching{
            repository.getAll()
        }.fold(
            onSuccess = { studentFlow ->
                Result.Success(studentFlow)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred.")
            }
        )
    }

    override suspend fun getStudent(studentID: Long): Result<Students?> {
        return runCatching{
            repository.getById(studentID)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred.")
            }
        )
    }


}

package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.StudentRepository
import sru.edu.sru_lib_management.core.util.CUDResult

@Service
class StudentService(
   @Qualifier("studentRepositoryImp") private val repository: StudentRepository
) : IStudentService {

    override suspend fun saveStudent(students: Students): CUDResult{
        return runCatching {
            repository.save(students)
        }.fold(
            onSuccess = { student ->
                CUDResult.Success(student)
            },
            onFailure = { e ->
                CUDResult.Failure(e.message ?: "Unknown error occurred.")
            }
        )
    }

    override suspend fun updateStudent(students: Students): CUDResult {
        return runCatching {
            repository.update(students)
        }.fold(
            onSuccess = {
                CUDResult.Success(students)
            },
            onFailure = {e ->
                CUDResult.Failure(e.message ?: "Unknown error occurred.")
            }
        )
    }
    override suspend fun deleteStudent(studentID: Long): CUDResult {
        return kotlin.runCatching {
            repository.delete(studentID)
        }.fold(
            onSuccess = {
                CUDResult.Success(true)
            },
            onFailure = { e ->
                CUDResult.Failure(e.message ?: "Unknown error occurred.")
            }
        )
    }

    override fun getStudents(): Flow<Students> {
        return repository.getAll()
    }

    override suspend fun getStudent(studentID: Long): Students? {
        return repository.getById(studentID)
    }


}

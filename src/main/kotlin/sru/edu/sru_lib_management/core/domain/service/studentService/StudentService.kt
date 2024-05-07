package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.StudentRepository

@Service
class StudentService(
   @Qualifier("studentRepositoryImp") private val repository: StudentRepository
) : IStudentService {

    override suspend fun saveStudent(students: Students): Students?{
        return runCatching {
            repository.save(students)
        }.fold(
            onSuccess = {student ->
                student
            },
            onFailure = {e ->
                e.message
                null
            }
        )
    }

    override suspend fun updateStudent(students: Students): Students? {
        return runCatching {
            repository.update(students)
        }.fold(
            onSuccess = {
                it
            },
            onFailure = {e ->
                e.message
                null
            }
        )
    }
    override suspend fun deleteStudent(studentID: Long): Boolean {
        return try {
            repository.delete(studentID)
            true
        }catch (e: Exception){
            false
        }
    }

    override fun getStudents(): Flow<Students> {
        return repository.getAll()
    }

    override suspend fun getStudent(studentID: Long): Students? {
        return repository.getById(studentID)
    }


}

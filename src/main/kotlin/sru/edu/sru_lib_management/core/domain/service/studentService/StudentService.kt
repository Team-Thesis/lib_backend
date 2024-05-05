package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.StudentRepository
import sru.edu.sru_lib_management.core.util.CallBack

@Service
class StudentService(
   @Qualifier("studentRepositoryImp") private val repository: StudentRepository
) : IStudentService {

    override suspend fun saveStudent(students: Students, callBack: CallBack) {
        val student = studentParam(students)
        return try {
            repository.save(data = student)
            callBack.onSuccess()
        }catch (e: Exception){
            callBack.onFailure("Error occurred while saving student: ${e.message}")
        }
    }
    override suspend fun updateStudent(students: Students, callBack: CallBack) {
        val student = studentParam(students)
        return try {
            repository.update(student)
            callBack.onSuccess()
        }catch (e: Exception){
            callBack.onFailure("Error occurred while update student: ${e.message}")
        }
    }
    override suspend fun deleteStudent(studentID: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun getStudents(): Flow<Students> {
        return repository.getAll()
    }

    override suspend fun getStudent(studentID: Long): Students? {
        return repository.getById(studentID)
    }

    private fun studentParam(students: Students): Students = Students(
        students.studentID,
        students.studentName,
        students.gender,
        students.dateOfBirth,
        students.degreeLevelID,
        students.majorID,
        students.generation
    )

}

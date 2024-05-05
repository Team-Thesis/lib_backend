package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.StudentRepository
import sru.edu.sru_lib_management.core.util.SaveCallBack

@Service
class StudentService(
   @Qualifier("studentRepositoryImp") private val repository: StudentRepository
) : IStudentService {

    override suspend fun saveStudent(students: Students, callBack: SaveCallBack): Students? {
        val student = Students(
            studentID = students.studentID,
            studentName = students.studentName,
            gender = students.gender,
            dateOfBirth = students.dateOfBirth,
            degreeLevelID = students.degreeLevelID,
            majorID = students.majorID,
            generation = students.generation
        )
        repository.save(data = student, callBack)
        return student
    }

    override suspend fun updateStudent(students: Students): Students {
        val student = Students(
            students.studentID,
            students.studentName,
            students.gender,
            students.dateOfBirth,
            students.degreeLevelID,
            students.majorID,
            students.generation
        )
        return repository.update(student)
    }

    override suspend fun deleteStudent(studentID: Long): Boolean {
        return repository.delete(studentID)
    }

    override fun getStudents(): Flow<Students> {
        return repository.getAll()
    }


    override suspend fun getStudent(studentID: Long): Students? {
        return repository.getById(studentID)
    }
}

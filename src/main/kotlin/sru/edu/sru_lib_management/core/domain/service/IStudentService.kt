package sru.edu.sru_lib_management.core.domain.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.common.Result

@Service
interface IStudentService {
    suspend fun saveStudent(students: Students): Result<Students?>
    suspend fun updateStudent(studentID: Long, students: Students): Result<Students?>
    suspend fun deleteStudent(studentID: Long): Result<Boolean>
    fun getStudents(): Result<Flow<Students>>
    suspend fun getStudent(studentID: Long): Result<Students?>
}

package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.util.CUDResult

interface IStudentService {
    suspend fun saveStudent(students: Students): CUDResult
    suspend fun updateStudent(students: Students): CUDResult
    suspend fun deleteStudent(studentID: Long): CUDResult
    fun getStudents(): Flow<Students>
    suspend fun getStudent(studentID: Long): Students?
}

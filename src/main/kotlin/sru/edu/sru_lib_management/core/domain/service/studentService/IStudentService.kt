package sru.edu.sru_lib_management.core.domain.service.studentService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.util.SaveCallBack

interface IStudentService {
    suspend fun saveStudent(students: Students, callBack: SaveCallBack): Students?
    suspend fun updateStudent(students: Students): Students?
    suspend fun deleteStudent(studentID: Long): Boolean?
    fun getStudents(): Flow<Students>
    suspend fun getStudent(studentID: Long): Students?
}

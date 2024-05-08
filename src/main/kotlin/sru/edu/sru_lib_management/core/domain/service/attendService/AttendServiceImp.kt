package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository

@Service
class AttendServiceImp(
    @Qualifier("attendRepositoryImp") private val repository: AttendRepository
) : AttendService{
    override suspend fun saveAttend(attend: Attend, studentId: Long): Attend? {
        return try {
            repository.save(attend.copy(studentID = studentId))
        }catch (e: Exception){
            null
        }
    }

    override suspend fun updateAttend(attend: Attend): Attend? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttend(attendID: Long): Boolean{
        TODO("Not yet implemented")
    }

    override fun getAllAttend(): Flow<Students> {
        TODO("Not yet implemented")
    }

    override suspend fun getAttend(attendID: Long): Attend {
        TODO("Not yet implemented")
    }

}
package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import sru.edu.sru_lib_management.core.util.CallBack

@Service
class AttendServiceImp(
    @Qualifier("attendRepositoryImp") private val repository: AttendRepository
) : AttendService{
    override suspend fun saveAttend(students: Students, callBack: CallBack) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAttend(students: Students, callBack: CallBack) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttend(studentID: Long): Boolean? {
        TODO("Not yet implemented")
    }

    override fun getAllAttend(): Flow<Students> {
        TODO("Not yet implemented")
    }

    override suspend fun getAttend(studentID: Long): Attend {
        TODO("Not yet implemented")
    }
}
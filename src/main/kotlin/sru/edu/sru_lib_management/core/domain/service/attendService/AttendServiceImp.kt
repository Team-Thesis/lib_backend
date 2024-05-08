package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import sru.edu.sru_lib_management.core.util.Result

@Service
class AttendServiceImp(
    @Qualifier("attendRepositoryImp") private val repository: AttendRepository
) : AttendService{
    override suspend fun saveAttend(attend: Attend, studentId: Long): Result<Attend?> {
        return kotlin.runCatching {
            repository.save(attend.copy(studentID = studentId))
        }.fold(
            onSuccess = {att ->
                Result.Success(att)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred.")
            }
        )
    }

    override suspend fun updateAttend(attend: Attend): Result<Attend?> {
        return runCatching {
            repository.update(attend)
        }.fold(
            onSuccess = { att ->
                Result.Success(att)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred.")
            }
        )
    }

    override suspend fun deleteAttend(attendID: Long): Result<Boolean> {
        return runCatching{
            repository.delete(attendID)
        }.fold(
            onSuccess = {
                Result.Success(true)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred.")
            }
        )
    }

    override fun getAllAttend(): Result<Flow<Attend>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAttend(attendID: Long): Result<Attend?> {
        TODO("Not yet implemented")
    }

}
package sru.edu.sru_lib_management.core.domain.service.attendService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import sru.edu.sru_lib_management.core.util.Result

@Service
class AttendServiceImp(
    @Qualifier("attendRepositoryImp") private val repository: AttendRepository
) : IAttendService{

    override suspend fun saveAttend(attend: Attend): Result<Attend?> {
        return runCatching {
            repository.save(attend)
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
        return kotlin.runCatching {
            repository.getAll()
        }.fold(
            onSuccess = {att ->
                Result.Success(att)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred while get all attend.")
            }
        )
    }

    override suspend fun getAttend(attendID: Long): Result<Attend?> {
        return runCatching{
            repository.getById(attendID)
        }.fold(
            onSuccess = {att ->
                Result.Success(att)
            },
            onFailure = {
                Result.Failure(it.message ?: "An error occurred while get attend.")
            }
        )
    }

}
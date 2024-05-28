package sru.edu.sru_lib_management.core.domain.service.implementation

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.service.IAttendService
import java.time.LocalDate
import java.time.LocalTime

@Service
class AttendServiceImp(
    @Qualifier("attendRepositoryImp") private val repository: AttendRepository
) : IAttendService {

    override suspend fun saveAttend(attend: Attend): Result<Attend?> {
        return runCatching {
            repository.save(attend)
        }.fold(
            onSuccess = {att ->
                Result.Success(att)
            },
            onFailure = {e ->
                if (e is Exception) {
                    e.printStackTrace()
                    Result.Failure("${e.message}")
                }
                else
                    Result.ClientError("User input error")
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

    override suspend fun getCustomAttByDate(date: Int): Result<Flow<Attend>> {
        return runCatching {
            if (date < 0)
                Result.ClientError("Opp!")
            repository.getCustomAttend(date)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                Result.Failure("${it.message}")
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

    override suspend fun getAttByStudentID(studentID: Long, date: LocalDate): Result<Attend?> {
        return runCatching{
            repository.getAttendByStudentID(studentID, date)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                Result.Failure("${it.message}")
            }
        )
    }

    override suspend fun updateExitingTime(studentID: Long, date: LocalDate, exitingTimes: LocalTime): Result<Boolean> {
        return runCatching {
            val findExistAttend = repository.getAttendByStudentID(studentID, date)
            println(findExistAttend)
            if (findExistAttend == null){
                Result.ClientError("Can not find attend with this student id: $studentID")
            }
            repository.updateExitingTime(exitingTimes, studentID, date)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                Result.Failure("${it.message}")
            }
        )
    }

    override suspend fun countAttendCustomTime(date: Int): Result<Int?> {
        return if (date < 0){
            Result.ClientError("Opp!")
        }else{
            runCatching {
                repository.count(date)
            }.fold(
                onSuccess = {
                    Result.Success(it)
                },
                onFailure = {
                    Result.Failure("${it.message}")
                }
            )
        }
    }

}
package sru.edu.sru_lib_management.core.domain.service.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.Analytic
import sru.edu.sru_lib_management.core.domain.dto.AttendDto
import sru.edu.sru_lib_management.core.domain.dto.dashbord.CardData
import sru.edu.sru_lib_management.core.domain.dto.dashbord.TotalMajorVisitor
import sru.edu.sru_lib_management.core.domain.dto.dashbord.WeeklyVisitor
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import sru.edu.sru_lib_management.core.domain.service.IAttendService
import java.time.LocalDate
import java.time.LocalTime

@Component
class AttendService(
    @Qualifier("attendRepositoryImp") private val repository: AttendRepository
) : IAttendService {

    /*
    * Save new Attend
    * */
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

    /*
    * Update attend
    * */
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

    /*
    * Delete attend
    * */
    override suspend fun deleteAttend(attendId: Long): Result<Boolean> {
        return runCatching{
            repository.delete(attendId)
        }.fold(
            onSuccess = {
                Result.Success(true)
            },
            onFailure = {
                Result.Failure(it.message ?: "Unknown error occurred.")
            }
        )
    }

    /*
    * Select custom attend by date
    * */
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

    /*
    * Get all attend
    * */
    override fun getAllAttend(): Result<Flow<Attend>> {
        return runCatching {
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

    /*
    * Select attend by id
    * */
    override suspend fun getAttend(attendId: Long): Result<Attend?> {
        return runCatching{
            repository.getById(attendId)
        }.fold(
            onSuccess = {att ->
                Result.Success(att)
            },
            onFailure = {
                Result.Failure(it.message ?: "An error occurred while get attend.")
            }
        )
    }

    /*
    * Get attend by student id
    * */
    override suspend fun getAttByStudentID(studentId: Long, date: LocalDate): Result<Attend?> {
        return runCatching{
            repository.getAttendByStudentID(studentId, date)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                println(it.printStackTrace())
                Result.Failure("${it.message}")
            }
        )
    }

    /*
    * Update exiting time when student go out
    * */
    override suspend fun updateExitingTime(studentId: Long, date: LocalDate, exitingTimes: LocalTime): Result<Boolean> {
        return runCatching {
            val findExistAttend = repository.getAttendByStudentID(studentId, date)
            println(findExistAttend)
            if (findExistAttend == null){
                Result.ClientError("Can not find attend with this student id: $studentId")
            }
            repository.updateExitingTime(exitingTimes, studentId, date)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = {
                println(it.printStackTrace())
                Result.Failure("${it.message}")
            }
        )
    }

    /*
    * Count attend custom by time
    * */
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
    override suspend fun getWeeklyVisit(): Result<WeeklyVisitor> {
        return runCatching {
            repository.getWeeklyVisit()
        }.fold(
            onSuccess = {
                Result.Success(WeeklyVisitor(it))
            },
            onFailure = {
                println(it.printStackTrace())
                Result.Failure("${it.message}")
            }
        )
    }

    override suspend fun analyticAttend(
        date: LocalDate, period: Int
    ): Result<Analytic> = runCatching {

        val areFieldBlank = date > LocalDate.now() || period == 0
        val writeInput = period == 1 || period == 7 || period == 30 || period == 365

        if (areFieldBlank || !writeInput)
            return Result.ClientError("Invalid data input.")

        val getCount = repository.countCurrentAndPreviousAttend(date, period)
        val currentValue: Int = getCount.currentValue
        val previousValue = getCount.previousValue

        val percentageChange: Float = if (previousValue == 0){
            if (currentValue == 0) 0f else 100f
        }else{
            ((currentValue - previousValue)).toFloat() / previousValue * 100
        }
        // Return this
        Analytic(currentValue = currentValue, percentage = percentageChange)
    }.fold(
        onSuccess = { data ->
            Result.Success(data)
        },
        onFailure = { e ->
            println(e.printStackTrace())
            Result.Failure(e.message.toString())
        }
    )

    override suspend fun getAttendDetails(): Result<List<AttendDto>> = runCatching {
        val attend = repository.getAttendDetail()
        attend.forEach {
            if (it.exitingTimes == null){
                it.status = "IN"
            }
            else it.status = "OUT"
        }
        attend
    }.fold(
        onSuccess = {
            Result.Success(it)
        },
        onFailure = {
            println(it.printStackTrace())
            Result.Failure(it.message.toString())
        }
    )

    override suspend fun getTotalMajorVisit(): Result<TotalMajorVisitor> = runCatching {
        repository.totalMajorVisit()
    }.fold(
        onSuccess = {
            Result.Success(it)
        },
        onFailure = {
            println(it.printStackTrace())
            Result.Failure(it.message.toString())
        }
    )

}
package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.service.attendService.AttendServiceImp
import sru.edu.sru_lib_management.core.util.Result
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

@Controller
@RequestMapping("api/v1/att")
class AttendController(
    private val service: AttendServiceImp
) {

    /*
    * ->  http://localhost:8090/api/v1/att
    * Save Attend when Student attend library
    * */
    @PostMapping
    suspend fun saveAtt(
        @RequestBody attend: Attend
    ): ResponseEntity<Any> = coroutineScope {
        if (attend.purpose.isBlank())
            ResponseEntity.status(HttpStatus.CONFLICT)
        val attends = Attend(
            attendID = null,
            studentID = attend.studentID,
            entryTimes = attend.entryTimes,
            exitingTimes = null,
            purpose = attend.purpose,
            date = attend.date
        )
        when(val result = service.saveAttend(attends)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.CREATED)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
        }
    }

    /*
    * -> http://localhost:8090/api/v1/att
    * Use to update Attend if necessary
    * */
    @PutMapping("/{attId}")
    suspend fun updateAtt(
        @RequestBody attend: Attend,
        @PathVariable attId: Long
    ): ResponseEntity<Attend?> = coroutineScope {
        when(val result = service.updateAttend(attend.copy(attendID = attId))){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.ACCEPTED)
            is Result.Failure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            is Result.ClientError -> ResponseEntity.badRequest().build()
        }
    }

    /*
    * ->  http://localhost:8090/api/v1/att
    * Get all attend
    * */
    @GetMapping
    suspend fun getAllAttend(): ResponseEntity<Flow<Attend>> = coroutineScope {
        when(val result = service.getAllAttend()){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.OK)
            is Result.Failure -> ResponseEntity(HttpStatus.BAD_REQUEST)
            is Result.ClientError -> ResponseEntity.badRequest().build()
        }
    }

    /*
    * ->  http://localhost:8090/api/v1/att
    * this end point use to update exiting time
    * */
    @PutMapping
    suspend fun updateExitingTime(
        @RequestParam studentID: Long,
        @RequestParam date: LocalDate,
        @RequestParam exitingTimes: LocalTime
    ): ResponseEntity<Any> = coroutineScope{
        when(val result = service.updateExitingTime(studentID, date, exitingTimes)){
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
            is Result.Success -> ResponseEntity(result.data, HttpStatus.ACCEPTED)
        }
    }

    /*
    -> http://localhost:8090/api/v1/att/custom get attend custom by time
    *  Example : Get Last 1 day, 7 days, 1 month or 1 year
    *
    */
    @GetMapping("/custom")
    suspend fun getCustomAttend(
        @RequestParam date: Int
    ): ResponseEntity<Any> = coroutineScope{
        when(val result = service.getCustomAttByDate(date)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.OK)
            is Result.Failure -> ResponseEntity.internalServerError().build()
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
        }
    }

    /*
    * ->  http://localhost:8090/api/v1/att/count
    * Count number of student by custom time
    * */
    @GetMapping("/count")
    suspend fun countCustomAttend(
        @RequestParam date: Int
    ): ResponseEntity<Any> = coroutineScope{
        when(val result = service.countAttendCustomTime(date)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.OK)
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.coroutineScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.service.attendService.AttendServiceImp
import sru.edu.sru_lib_management.core.util.Result

@Controller
@RequestMapping("api/v1/att")
class AttendController(
    private val service: AttendServiceImp
) {

    @PostMapping
    @Transactional
    suspend fun saveAtt(
        @RequestBody attend: Attend
    ): ResponseEntity<Attend?> = coroutineScope {
        if (attend.purpose.isBlank())
            ResponseEntity.status(HttpStatus.CONFLICT)
        when(val result = service.saveAttend(attend)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.CREATED)
            is Result.Failure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/{attId}")
    suspend fun updateAtt(
        @RequestBody attend: Attend,
        @PathVariable attId: Long
    ): ResponseEntity<Attend?> = coroutineScope {
        when(val result = service.updateAttend(attend.copy(attendID = attId))){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.ACCEPTED)
            is Result.Failure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
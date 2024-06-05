package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import sru.edu.sru_lib_management.core.domain.service.IBorrowService
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/borrow")
class BorrowController (
    @Qualifier("borrowService") private val service: IBorrowService
){

    @GetMapping("/count")
    suspend fun countAndCompareBorrowsForPeriod(
        @RequestParam period: Int,
        @RequestParam endDate: LocalDate
    ): ResponseEntity<Any> = coroutineScope {
        when(val result = service.countBorrowForPeriod(period, endDate)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.OK)
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping
    suspend fun saveBorrow(
        @RequestBody borrowBook: BorrowBook
    ): ResponseEntity<Any> = coroutineScope {
        when(val result = service.saveBorrow(borrowBook)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.CREATED)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    suspend fun getAllBorrow(): ResponseEntity<Flow<BorrowBook>> = coroutineScope {
        when(val result = service.getBorrows()){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.OK)
            is Result.Failure -> ResponseEntity.internalServerError().build()
            is Result.ClientError -> ResponseEntity.badRequest().build()
        }
    }
}
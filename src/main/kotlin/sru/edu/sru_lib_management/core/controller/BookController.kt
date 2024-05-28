package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.service.IBookService

@RestController
@RequestMapping("/api/v1/book")
class BookController(
    private val service: IBookService
) {

    /*
    * -> http://localhost:8090/api/v1/book
    * This Endpoint use to add book to database
    * */
    @PostMapping
    suspend fun addNewBook(
        @RequestBody books: Books
    ): ResponseEntity<Any> = coroutineScope {
        when(val result = service.saveBook(books)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.CREATED)
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /*
   * -> http://localhost:8090/api/v1/book
   * This Endpoint use to get all book from database
   * */
    @GetMapping
    suspend fun getBooks(): ResponseEntity<Flow<Books>> = coroutineScope {
        when(val result = service.getAllBooks()){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.OK)
            is Result.ClientError -> ResponseEntity.badRequest().build()
            is Result.Failure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /*
   * -> http://localhost:8090/api/v1/book/id
   * This Endpoint use to update book in database
   * */
    @PutMapping("/{bookId}")
    suspend fun updateBook(
        @PathVariable bookId: String,
        @RequestBody books: Books
    ): ResponseEntity<Any> = coroutineScope {
        if (books.bookId != bookId)
            ResponseEntity("Book ID in path and body do not match", HttpStatus.BAD_REQUEST)
        when(val result = service.updateBook(books)){
            is Result.Success -> ResponseEntity(result.data, HttpStatus.ACCEPTED)
            is Result.ClientError -> ResponseEntity(result.clientErrMsg, HttpStatus.BAD_REQUEST)
            is Result.Failure -> ResponseEntity(result.errorMsg, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
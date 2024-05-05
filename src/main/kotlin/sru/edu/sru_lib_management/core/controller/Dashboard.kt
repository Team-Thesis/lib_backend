package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.bookService.BookService
import sru.edu.sru_lib_management.core.domain.service.studentService.StudentService

@RestController
@RequestMapping("api/v1/dashboard")
class Dashboard(
    private val bookService: BookService,
    private val studentService: StudentService
){

    @GetMapping("/students")
    suspend fun getStudents(): ResponseEntity<Flow<Students>> {
        val students: Flow<Students> = studentService.getStudents()
        return ResponseEntity.ok(students)
    }

    @GetMapping("/books")
    fun getAllBook(): ResponseEntity<Flow<Books>> {
        val books = bookService.getAllBooks()
        return ResponseEntity.ok(books)
    }
}
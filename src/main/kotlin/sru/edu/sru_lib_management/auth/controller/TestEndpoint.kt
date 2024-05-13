package sru.edu.sru_lib_management.auth.controller

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.studentService.StudentService
import sru.edu.sru_lib_management.core.util.Result

@RestController
@RequestMapping("/api/v1/secret")
class TestEndpoint(
    private val studentService: StudentService
){

    @GetMapping("/hello")
    fun helloWorld(): String = "Hello World!"

    @GetMapping("/student")
    fun getAllStudents(): ResponseEntity<Flow<Students>> {
        return when(val result = studentService.getStudents()){
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.noContent().build()
        }
    }
}
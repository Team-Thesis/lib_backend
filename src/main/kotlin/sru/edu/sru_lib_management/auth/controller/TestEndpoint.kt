package sru.edu.sru_lib_management.auth.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.implementation.StudentService
import sru.edu.sru_lib_management.core.common.Result

@RestController
@RequestMapping("/api/v1/secret")
class TestEndpoint(
    private val studentService: StudentService
){

    @GetMapping("/hello")
    suspend fun helloWorld(): String = "Hello World!"

    @GetMapping("/student")
    fun getAllStudents(): ResponseEntity<Flow<Students>> {
        return when(val result = runBlocking {studentService.getStudents()}){
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.noContent().build()
            is Result.ClientError -> ResponseEntity.badRequest().build()
        }
    }
}
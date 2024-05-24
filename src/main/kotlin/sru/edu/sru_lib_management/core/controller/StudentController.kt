package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.studentService.StudentService
import sru.edu.sru_lib_management.core.util.Result


@RestController
@RequestMapping("api/v1/student")
class StudentController(
    private val service: StudentService
) {

    /*
     * Add student to database
     */
    @PostMapping
    @Transactional
    suspend fun saveStudent(
        @RequestBody students: Students
    ): ResponseEntity<Students?> = coroutineScope {
        val areFieldBlank = students.studentID == null || students.studentName.isBlank() || students.gender.isBlank()
        if (!areFieldBlank){
            when(val result = service.saveStudent(students)){
                is Result.Success -> ResponseEntity(result.data, HttpStatus.CREATED)
                is Result.Failure -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
                is Result.ClientError -> ResponseEntity.badRequest().build()
            }
        }
        else
            ResponseEntity(HttpStatus.CONFLICT)
    }

    // Get all student using flux as flow
    @GetMapping
    @FlowPreview
    suspend fun getAllStudents(): ResponseEntity<Flow<Students>> = coroutineScope {
        when(val result = service.getStudents()){
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.noContent().build()
            is Result.ClientError -> ResponseEntity.badRequest().build()
        }
    }

    /*
     * get student from database by id
     */
    @GetMapping("/{studentID}")
    @ResponseBody
    suspend fun getStudentById(
        @PathVariable studentID: Long,
    ): ResponseEntity<Students> = coroutineScope{
        // get student
        when(val student = service.getStudent(studentID)){
            is Result.Success -> ResponseEntity.ok(student.data)
            is Result.Failure -> ResponseEntity.noContent().build()
            is Result.ClientError -> ResponseEntity.badRequest().build()
        }
    }

    /*
     * Update student from database
     */
    @PutMapping("/{studentID}")
    @ResponseBody
    suspend fun updateStudent(
        @PathVariable studentID: Long,
        @RequestBody students: Students
    ): ResponseEntity<Students> = coroutineScope {
        when(val result = service.updateStudent(studentID, students)) {
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.internalServerError().build()
            is Result.ClientError -> ResponseEntity.notFound().build()
        }
    }

    /*
    * Delete student from database
     */
    @PostMapping("/delete/{studentID}")
    suspend fun deleteStudent(@PathVariable studentID: Long) {
        when(val result = service.deleteStudent(studentID)){
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.badRequest().body(result.errorMsg)
            is Result.ClientError -> ResponseEntity.badRequest().body(result.clientErrMsg)
        }
    }
}

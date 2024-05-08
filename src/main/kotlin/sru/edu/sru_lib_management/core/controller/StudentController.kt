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
    suspend fun saveStudent(@RequestBody students: Students): ResponseEntity<Any> = coroutineScope {
        val areFieldBlank = students.studentID == null || students.studentName.isBlank() || students.gender.isBlank()
        if (!areFieldBlank){
            when(val saveResult = service.saveStudent(students)){
                is Result.Success -> ResponseEntity.ok().body(saveResult.data)
                is Result.Failure -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(saveResult.errorMsg)
            }
        }
        else
            ResponseEntity.badRequest().body("One or more fields are blank.")
    }

    // Get all student using flux as flow
    @GetMapping
    suspend fun getAllStudents(): ResponseEntity<Flow<Students>> = coroutineScope {
        when(val result = service.getStudents()){
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.noContent().build()
        }
    }

    /*
     * get student from database by id
     */
    @GetMapping("/{studentID}")
    @ResponseBody
    suspend fun getStudentById(
        @PathVariable studentID: Long
    ): ResponseEntity<Students> = coroutineScope{
        // get student
        when(val student = service.getStudent(studentID)){
            is Result.Success -> ResponseEntity.ok(student.data)
            is Result.Failure -> ResponseEntity.noContent().build()
        }
    }

    /*
     * Update student from database
     */
    @PostMapping("/{studentID}")
    @ResponseBody
    suspend fun updateStudent(
        @RequestBody student: Students,
        @PathVariable studentID: Long
    ) {
        service.updateStudent(student.copy(studentID = studentID))
    }

    /*
    * Delete student from database
     */
    @PostMapping("/delete/{studentID}")
    suspend fun deleteStudent(@PathVariable studentID: Long) {
        when(val result = service.deleteStudent(studentID)){
            is Result.Success -> ResponseEntity.ok().body(result.data)
            is Result.Failure -> ResponseEntity.badRequest().body(result.errorMsg)
        }
    }
}

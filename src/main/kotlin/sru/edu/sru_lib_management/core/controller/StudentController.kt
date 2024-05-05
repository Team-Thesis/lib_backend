package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.studentService.StudentService
import sru.edu.sru_lib_management.core.util.CallBack


@RestController
@RequestMapping("api/v1/student")
class StudentController(
    private val service: StudentService
) {

    val callBack = object : CallBack{
        override fun onSuccess() {
            ResponseEntity.status(HttpStatus.CREATED).build<Any>()
        }

        override fun onFailure(error: String) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
        }
    }
    /*
     * Add student to database
     */
    @PostMapping
    @Transactional
    suspend fun saveStudent(@RequestBody students: Students): ResponseEntity<String> {
        //Save student
        service.saveStudent(students, callBack)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Operation is still processing")
    }

    @GetMapping
    suspend fun getAllStudents(): ResponseEntity<Flow<Students>> {
        /*
         * Get all students from database
         */
        val getStudents = service.getStudents()
        return ResponseEntity(getStudents, HttpStatus.OK)
    }

    /*
     * get student from database
     */
    @GetMapping("/{studentID}")
    @ResponseBody
    suspend fun getStudentById(@PathVariable studentID: Long): ResponseEntity<Students> {
        // get student
        val student = service.getStudent(studentID)
        return if (student != null) {
            ResponseEntity(student, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /*
     * Update student from database
     */
    @PostMapping("/{studentID}")
    @ResponseBody
    suspend fun updateStudent(@RequestBody student: Students, @PathVariable studentID: Long): ResponseEntity<Students> {
        service.updateStudent(student.copy(studentID = studentID), callBack)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    /*
    * Delete student from database
     */
    @PostMapping("/delete/{studentID}")
    suspend fun deleteStudent(@PathVariable studentID: Long): ResponseEntity<Unit> {
        val deleted: Boolean = service.deleteStudent(studentID)

        return if (deleted)
            ResponseEntity(HttpStatus.NO_CONTENT)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }
}

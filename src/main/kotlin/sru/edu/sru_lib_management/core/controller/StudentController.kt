package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.studentService.StudentService
import sru.edu.sru_lib_management.core.util.SaveCallBack

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
    suspend fun saveStudent(@RequestBody students: Students?, callBack: SaveCallBack): ResponseEntity<Students> {
        //Save student
        val saveStudent = service.saveStudent(students!!, callBack)
        // if it cannot save return conflict else success
        return ResponseEntity(saveStudent, HttpStatus.CREATED)
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
        val updateStudent = service.updateStudent(student.copy(studentID = studentID))
        return ResponseEntity(updateStudent, HttpStatus.OK)
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

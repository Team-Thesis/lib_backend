package sru.edu.sru_lib_management.core.controller

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.service.studentService.StudentService


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
    suspend fun saveStudent(@RequestBody students: Students): ResponseEntity<Students?> = coroutineScope {
        val areFieldBlank = students.studentID == null || students.studentName.isBlank() || students.gender.isBlank()
        if (!areFieldBlank){
            val student = service.saveStudent(students)
            ResponseEntity(student, HttpStatus.CREATED)
        }
        else
            ResponseEntity.badRequest().body(null)
    }

    @GetMapping
    suspend fun getAllStudents(): ResponseEntity<Flow<Students>> = coroutineScope {
        /*
         * Get all students from database
         */
        val getAllStudent = service.getStudents()
        ResponseEntity(getAllStudent, HttpStatus.OK)
    }

    /*
     * get student from database
     */
    @GetMapping("/{studentID}")
    @ResponseBody
    suspend fun getStudentById(
        @PathVariable studentID: Long
    ): ResponseEntity<Students> = coroutineScope{
        // get student
        val student = service.getStudent(studentID)
        if (student != null)
            ResponseEntity(student, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)

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
        service.deleteStudent(studentID)
    }
}

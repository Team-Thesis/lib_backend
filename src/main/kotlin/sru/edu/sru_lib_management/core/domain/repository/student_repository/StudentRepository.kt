package sru.edu.sru_lib_management.core.domain.repository.student_repository

import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository

@Repository
interface StudentRepository : ICrudRepository<Students, Long>

package sru.edu.sru_lib_management.core.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.domain.dto.dashbord.Dashboard
import sru.edu.sru_lib_management.core.domain.service.implementation.BookService
import sru.edu.sru_lib_management.core.domain.service.implementation.StudentService

@RestController
@RequestMapping("api/v1/dashboard")
class DashboardController(

){
    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    suspend fun dashboard(): Dashboard {
        TODO()
    }

}
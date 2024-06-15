package sru.edu.sru_lib_management.core.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.dashbord.Dashboard
import sru.edu.sru_lib_management.core.domain.service.IAttendService
import sru.edu.sru_lib_management.core.domain.service.IBookService
import sru.edu.sru_lib_management.core.domain.service.IBorrowService
import java.time.LocalDate

@RestController
@RequestMapping("api/v1/dashboard")
class DashboardController(
    private val bookService: IBookService,
    @Qualifier("attendService") private val attendService: IAttendService,
    @Qualifier("borrowService") private val borrowService: IBorrowService
){

    //http://localhost:8090/api/v1/dashboard
    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    suspend fun dashboard(): Dashboard {
        val bookAvailable: Any = when(val result = bookService.getAvailableBook()){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }
        val entryToday: Any = when(val result = attendService.countAndCompareAttend(LocalDate.now(), 1)){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }
        val borrowToday: Any = when(val result =borrowService.compareCurrentAndPreviousBorrow(LocalDate.now(), 1)){
            is Result.Success -> result.data
            is Result.Failure ->  result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }
        val customEntry: Any = when(val result = attendService.getAttendDetails()){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }

        val weeklyVisitor: Any = when(val result = attendService.getWeeklyVisit()){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }
        val entryThisMonth: Any = when(val result = attendService.countAndCompareAttend(LocalDate.now(), 30)){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }
        val totalMajorVisitor: Any = when(val result = attendService.getTotalMajorVisit()){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }

        return Dashboard(
            entryToday = entryToday,
            borrowToday = borrowToday,
            customEntry = customEntry,
            totalBookOfThisMonth = bookAvailable,
            totalEntryThisMonth = entryThisMonth,
            totalMajorVisitor = totalMajorVisitor,
            weeklyVisitor = weeklyVisitor
        )
    }

}
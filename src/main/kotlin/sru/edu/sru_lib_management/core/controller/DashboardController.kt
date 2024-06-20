package sru.edu.sru_lib_management.core.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.Analytic
import sru.edu.sru_lib_management.core.domain.dto.dashbord.CardData
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
    private var card: List<CardData>? = null

    //http://localhost:8090/api/v1/dashboard
    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    suspend fun dashboard(): Dashboard {

        val entryToday: Analytic = when(val result = attendService.analyticAttend(LocalDate.now(), 1)){
            is Result.Success -> result.data
            is Result.Failure -> Analytic(-0, -0f)
            is Result.ClientError -> Analytic(-0, -0f)
        }
        val entryThisMonth: Analytic = when(val result = attendService.analyticAttend(LocalDate.now(), 30)){
            is Result.Success -> result.data
            is Result.Failure -> Analytic(-0, -0f)
            is Result.ClientError -> Analytic(-0, -0f)
        }
        val borrowToday: Analytic = when(val result = borrowService.analyticBorrow(LocalDate.now(), 1)){
            is Result.Success -> result.data
            is Result.Failure -> Analytic(-0, -0f)
            is Result.ClientError -> Analytic(-0, -0f)
        }
        val bookAvailable: Any = when(val result = bookService.getAvailableBook()){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
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
        val totalMajorVisitor: Any = when(val result = attendService.getTotalMajorVisit()){
            is Result.Success -> result.data
            is Result.Failure -> result.errorMsg
            is Result.ClientError -> result.clientErrMsg
        }
        /// Card
        card = listOf(
            CardData("Entry Today", entryToday.currentValue, entryToday.percentage),
            CardData("Book Borrow Today", borrowToday.currentValue, borrowToday.percentage),
            CardData("Book Sponsor", 0, 0f),
            CardData("Total Entry Of This Month", entryThisMonth.currentValue, entryThisMonth.percentage)
        )

        return Dashboard(
            cardData = card!!,
            totalMajorVisitor = totalMajorVisitor,
            weeklyVisitor = weeklyVisitor,
            bookAvailable = bookAvailable,
            customEntry = customEntry
        )
    }

}
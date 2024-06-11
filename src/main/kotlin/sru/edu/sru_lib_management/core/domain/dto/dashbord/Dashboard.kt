package sru.edu.sru_lib_management.core.domain.dto.dashbord

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.AttendDto
import sru.edu.sru_lib_management.core.domain.dto.BookAvailableDto
import sru.edu.sru_lib_management.core.domain.model.Attend

data class Dashboard(
    val entryToday: Any,
    val borrowToday: Any,
    val customEntry: Any,
    val totalBookOfThisMonth: Any,
    val totalEntryThisMonth: Any,
    val totalMajorVisitor: Any,
    val weeklyVisitor: Any
)
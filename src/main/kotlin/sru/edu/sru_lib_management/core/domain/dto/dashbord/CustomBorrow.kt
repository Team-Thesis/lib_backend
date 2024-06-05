package sru.edu.sru_lib_management.core.domain.dto.dashbord

import java.time.LocalDate

data class CustomBorrow(
    val count: Map<LocalDate, Int>,
    val percentageChange: Double
)
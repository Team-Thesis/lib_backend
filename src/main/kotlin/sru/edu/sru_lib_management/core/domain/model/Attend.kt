package sru.edu.sru_lib_management.core.domain.model

import java.time.LocalTime
import java.time.LocalDate

data class Attend (
    val attendId: Long?,
    val studentId: Long,
    val entryTimes: LocalTime,
    val exitingTimes: LocalTime?,
    val purpose: String,
    val date: LocalDate
)

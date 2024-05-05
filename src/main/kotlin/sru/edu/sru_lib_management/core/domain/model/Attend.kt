package sru.edu.sru_lib_management.core.domain.model

import java.sql.Timestamp
import java.time.LocalDate

data class Attend (
    val attendID: Long?,
    val studentID: Long,
    val entryTimes: Timestamp,
    val exitingTimes: Timestamp,
    val date: LocalDate,
    val purpose: String
)

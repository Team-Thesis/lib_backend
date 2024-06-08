package sru.edu.sru_lib_management.core.domain.dto

import java.time.LocalTime

data class AttendDto(
    val studentId: Long,
    val studentName: String,
    val gender: String,
    val major: String,
    val entryTimes: LocalTime,
    val exitingTimes: LocalTime?,
    val purpose: String,
    var status: String?
)

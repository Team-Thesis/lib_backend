package sru.edu.sru_lib_management.core.domain.model

import java.time.LocalDate
import java.util.*


data class Students (
    val studentID: Long?,
    val studentName: String,
    val gender: String,
    val dateOfBirth: LocalDate,
    val degreeLevelID: Int,
    val majorID: Int,
    val generation: Int
)

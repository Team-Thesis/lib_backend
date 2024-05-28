package sru.edu.sru_lib_management.core.domain.model

import java.time.LocalDate


data class Students (
    val studentId: Long?,
    val studentName: String,
    val gender: String,
    val dateOfBirth: LocalDate,
    val degreeLevelId: String,
    val majorId: String,
    val generation: Int
)

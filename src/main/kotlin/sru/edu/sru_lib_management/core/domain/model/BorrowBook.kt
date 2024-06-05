package sru.edu.sru_lib_management.core.domain.model

import java.time.LocalDate


class BorrowBook (
    val borrowId: Long?,
    val bookId: String,
    val studentId: Long,
    val borrowDate: LocalDate,
    val giveBackDate: LocalDate,
    val isBringBack: Boolean
)

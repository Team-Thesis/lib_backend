package sru.edu.sru_lib_management.core.domain.model

import java.sql.Date

class BorrowBook (
    val borrowId: Long?,
    val bookId: String,
    val studentId: Long,
    val borrowDate: Date,
    val giveBackDate: Date,
    val isBringBack: Boolean
)

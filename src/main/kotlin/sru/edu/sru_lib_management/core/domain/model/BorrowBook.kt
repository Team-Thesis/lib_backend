package sru.edu.sru_lib_management.core.domain.model

import java.sql.Date

class BorrowBook (
    val borrowID: Long?,
    val bookID: Long,
    val studentID: Long,
    val borrowDate: Date,
    val giveBackDate: Date,
    val isBringBack: Boolean
)

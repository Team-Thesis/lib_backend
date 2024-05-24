package sru.edu.sru_lib_management.core.domain.model

import java.time.LocalDate

data class BookSponsor (
    val sponsorID: Int?,
    val sponsorName: String,
    val bookID: String,
    val bookType: String,
    val numberOfBook: Int,
    val sponsorDate: LocalDate
)
package sru.edu.sru_lib_management.core.domain.model

import java.time.LocalDate

data class BookSponsorships (
    val bookId: String,
    val sponsorId: Int,
    val bookType: String,
    val numberOfBook: Int,
    val sponsorDate: LocalDate
)
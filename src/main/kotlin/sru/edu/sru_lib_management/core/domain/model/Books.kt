package sru.edu.sru_lib_management.core.domain.model

data class Books (
    val bookID: String?,
    val bookTitle: String,
    val number: Int,
    val sponsorID: Int,
    val languageID: Int,
    val collegeID: Int,
    val bookType: String,
)

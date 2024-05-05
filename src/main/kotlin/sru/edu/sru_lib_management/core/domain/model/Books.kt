package sru.edu.sru_lib_management.core.domain.model

data class Books (
    val bookID: Long?,
    val bookTitle: String,
    val languageID: Int,
    val collegeID: Int,
    val bookType: String,
)

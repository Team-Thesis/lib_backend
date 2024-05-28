package sru.edu.sru_lib_management.core.domain.model

data class Books (
    val bookId: String,
    val bookTitle: String,
    val number: Int,
    val languageId: String,
    val collegeId: String,
    val bookType: String,
)

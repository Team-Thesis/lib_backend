package sru.edu.sru_lib_management.core.data.query

object BorrowBookQuery {
    const val SAVE_BORROW_BOOK_QUERY: String =
        "INSERT INTO borrow_books (book_id, student_id, borrow_date, give_back_date, is_bring_back)" +
                "VALUES (:bookID, :studentID, :borrowDate, :giveBackDate, :isBringBack);"
    const val UPDATE_BORROW_BOOK_QUERY: String =
        "UPDATE borrow_books set book_id = :bookID, student_id = :studentID, borrow_date = :borrowDate, " +
                "give_back_date = :givBackDate, is_bring_back = :isBringBack;"
    const val DELETE_BORROW_BOOK_QUERY: String = "DELETE borrow_books WHERE borrow_id = :borrowID;"
    const val GET_BORROW_BOOKS_QUERY: String = "SELECT * FROM borrow_books;"
    const val GET_BORROW_BOOK_QUERY: String = "SELECT * FROM borrow_books WHERE borrow_id = :borrowID;" ////
}

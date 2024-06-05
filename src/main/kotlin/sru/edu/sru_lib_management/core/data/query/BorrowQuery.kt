package sru.edu.sru_lib_management.core.data.query

object BorrowQuery {
    const val SAVE_BORROW_QUERY: String =
        "INSERT INTO borrow_books (book_id, student_id, borrow_date, give_back_date, is_bring_back)" +
                "VALUES (:bookId, :studentId, :borrowDate, :giveBackDate, :isBringBack);"
    const val UPDATE_BORROW_QUERY: String =
        "UPDATE borrow_books set book_id = :bookId, student_id = :studentId, borrow_date = :borrowDate, " +
                "give_back_date = :givBackDate, is_bring_back = :isBringBack;"
    const val DELETE_BORROW_QUERY: String = "DELETE borrow_books WHERE borrow_id = :borrowId;"
    const val GET_BORROWS_QUERY: String = "SELECT * FROM borrow_books;"
    const val GET_BORROW_QUERY: String = "SELECT * FROM borrow_books WHERE borrow_id = :borrowId;" ////
}

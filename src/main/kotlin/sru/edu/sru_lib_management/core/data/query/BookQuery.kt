package sru.edu.sru_lib_management.core.data.query

object BookQuery {
    const val SAVE_BOOK_QUERY: String = "Insert into books (book_tittle, number, sponsor_id, language_id, collage_id, book_type) " +
            "VALUES (:bookTittle, :number, :sponsorID, :languageID, :collageID, :bookType);"

    const val UPDATE_BOOK_QUERY: String =
        "UPDATE books SET book_tittle = :bookTittle, number = :number, sponsor_id = :sponsorID, language_id = :languageID, collage_id = :collageID, " +
                "book_type = :bookType WHERE book_id = :bookID;"

    const val DELETE_BOOK_QUERY: String = "DELETE FROM books WHERE book_id = :bookID;"

    const val GET_BOOK_QUERY: String = "SELECT * FROM books WHERE book_id = :bookID;"

    const val GET_BOOKS_QUERY: String = "SELECT * FROM books;"
}

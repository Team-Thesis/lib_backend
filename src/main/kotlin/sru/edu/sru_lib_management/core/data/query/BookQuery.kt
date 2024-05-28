package sru.edu.sru_lib_management.core.data.query

object BookQuery {
    const val SAVE_BOOK_QUERY ="""
        INSERT INTO books (book_id, book_title, number, language_id, college_id, book_type)
        VALUES (:bookId, :bookTitle, :number, :languageId, :collegeId, :bookType);
    """

    const val UPDATE_BOOK_QUERY = """
        UPDATE books SET book_title = :bookTitle, number = :number, language_id = :languageId, 
        college_id = :collegeId, book_type = :bookType WHERE book_id = :bookId;
    """

    const val DELETE_BOOK_QUERY = "DELETE FROM books WHERE book_id = :bookId;"

    const val GET_BOOK_QUERY = "SELECT * FROM books WHERE book_id = :bookId;"

    const val GET_BOOKS_QUERY = "SELECT * FROM books;"

    const val SEARCH_BOOK_QUERY = "SELECT * FROM books WHERE 1=1"


    // ======== ================== =================================
    // Sponsor query
    const val SAVE_SPONSOR_QR = """
        INSERT INTO book_sponsors(sponsor_id, sponsor_name)
        VALUES (:sponsorId, :sponsorName);
    """
    const val UPDATE_SPONSOR_QR = """
        Update book_sponsors set sponsor_name = :sponsorName 
        WHERE sponsor_id = :sponsorId;
    """

    // ======== ================== =================================
    // Book sponsorship query
}

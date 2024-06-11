USE sru_library;

CREATE PROCEDURE GetAvailableBook()
BEGIN
    SELECT l.language_name,
           SUM(b.number) as total_book,
           SUM(b.number - IFNULL(bb.borrowed_count, 0)) as available_books
        FROM books b
            INNER JOIN language l on b.language_id = l.language_id
            LEFT JOIN (
                SELECT book_id, count(*) as borrowed_count
                From borrow_books WHERE is_bring_back = FALSE
                GROUP BY book_id
            ) bb ON b.book_id = bb.book_id
        GROUP BY l.language_name;
end;

CALL GetAvailableBook();

DROP PROCEDURE GetAvailableBook;
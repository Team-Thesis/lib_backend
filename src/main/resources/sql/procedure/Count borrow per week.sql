USE sru_library;
Create Procedure CountBorrowPerWeek()
BEGIN
    DECLARE startDate DATE;
    set startDate = CURDATE() - interval 7 day;
    SELECT borrow_date, COUNT(*) as count
    From borrow_books where borrow_date >= startDate GROUP BY borrow_date ORDER BY borrow_date;
end;
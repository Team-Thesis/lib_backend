USE sru_library;

# Get visitor base on time (last 1 day, last 7 day, last 1 month, ....)
CREATE PROCEDURE GetAttendByCustomTime(
    IN timeInterval INT
)
BEGIN
    DECLARE startDate DATE;
    #Calculate start date depend on time interval
    SET startDate = CASE
        WHEN timeInterval = 1 THEN CURDATE() - INTERVAL 1 DAY
        WHEN timeInterval = 7 THEN CURDATE() - INTERVAL 7 DAY
        WHEN timeInterval = 30 THEN CURDATE() - INTERVAL 30 DAY
        WHEN timeInterval = 365 THEN CURDATE() - INTERVAL 365 DAY
        ELSE CURDATE()
    END; #End case statement
    # Fetch data based on the start date
    SELECT * FROM attend WHERE date >= startDate;
END ;#end procedure

# Get all book borrow base on time (last 1 day, last 7 day, last 1 month, ....)
CREATE PROCEDURE GetBorrowBookByCustomTime(
    IN timeInterval INT
)BEGIN
    DECLARE startDate DATE;
    #Calculate start date depend on time interval
    SET startDate = CASE
        WHEN timeInterval = 1 THEN CURDATE() - INTERVAL 1 DAY
        WHEN timeInterval = 7 THEN CURDATE() - INTERVAL 7 DAY
        WHEN timeInterval = 30 THEN CURDATE() - INTERVAL 30 DAY
        WHEN timeInterval = 365 THEN CURDATE() - INTERVAL 365 DAY
        ELSE CURDATE()
    END;
    SELECT * from borrow_books WHERE borrow_date >= startDate;
END; #end procedure
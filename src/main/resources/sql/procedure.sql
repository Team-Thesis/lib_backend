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


#Count number of student that attend library custom by date
CREATE PROCEDURE CountAttendByCustomTime(
    IN timeInterval INT
)BEGIN
    DECLARE StartDate DATE;
    SET StartDate = CASE
        when timeInterval = 1 then CURDATE() - INTERVAL 1 DAY
        when timeInterval = 7 then CURDATE() - INTERVAL 7 DAY
        when timeInterval = 30 then CURDATE() - INTERVAL 30 DAY
        when timeInterval = 365 then CURDATE() - INTERVAL 365 DAY
    end ;
    SELECT COUNT(*) AS attendance_count From attend WHERE date >= StartDate;
end;


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


USE sru_library;
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








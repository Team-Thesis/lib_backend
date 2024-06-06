USE sru_library;
CREATE PROCEDURE CountAttendPerWeek()
begin
    DECLARE startDate DATE;
    SET startDate = CURDATE() - interval 7 day ;
    select date, COUNT(*) as count
    from attend where date >= startDate GROUP BY date ORDER BY date;
end;



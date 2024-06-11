USE sru_library;

CREATE PROCEDURE CountAttendPerWeek(IN Monday DATE, IN Sunday DATE)
begin
    select
        DAYNAME(date) as day_name,
        COUNT(*) as count
    from attend
    where date >= Monday AND date <= Sunday
    GROUP BY date
    ORDER BY date;
end;

CALL CountAttendPerWeek('2024-06-03', '2024-06-09');

DROP PROCEDURE CountAttendPerWeek;
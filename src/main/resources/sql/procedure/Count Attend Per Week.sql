USE sru_library;

CREATE PROCEDURE CountAttendPerWeek(IN Monday DATE, IN Sunday DATE)
BEGIN

    CREATE TEMPORARY TABLE date_series (
        date DATE
    );

    SET @startDate = Monday;
    WHILE @startDate <= Sunday DO
        INSERT INTO date_series VALUES (@startDate);
        SET @startDate = DATE_ADD(@startDate, INTERVAL 1 DAY);
    END WHILE;

    SELECT
        ds.date,
        DAYNAME(ds.date) AS day_name,
        COALESCE(COUNT(a.date), 0) AS count
    FROM
        date_series ds
            LEFT JOIN
        attend a ON ds.date = a.date
    GROUP BY
        ds.date
    ORDER BY
        ds.date;

    DROP TEMPORARY TABLE date_series;
END;


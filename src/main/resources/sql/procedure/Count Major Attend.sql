USE sru_library;

CREATE PROCEDURE CountMajorAttendLib()
BEGIN
    SELECT
        m.major_name AS Major,
        COUNT(a.attend_id) AS Amount,
        ROUND(COUNT(a.attend_id) * 100.0 / (SELECT COUNT(*) FROM attend), 2) as Percentage
    FROM
        majors m INNER JOIN (attend a INNER JOIN students s ON s.student_id = a.student_id)
        ON s.major_id = m.major_id
    GROUP BY
        m.major_name
    ORDER BY
        Amount DESC;
end;

Drop PROCEDURE CountMajorAttendLib;

Call CountMajorAttendLib()
USE sru_library;

CREATE PROCEDURE CountMajorAttendLib()
BEGIN
    SELECT
        m.major_name AS Major,
        COUNT(a.attend_id) AS Amount,
        COUNT(a.attend_id) / (SELECT COUNT(*) FROM attend WHERE DATE(date) = CURDATE()) * 100 AS Percentage
    FROM
        attend a
            JOIN
        students s ON a.student_id = s.student_id
            JOIN
        majors m ON s.major_id = m.major_id
    GROUP BY
        m.major_name
    ORDER BY
        Amount DESC;
end;

Drop PROCEDURE CountMajorAttendLib;
Call CountMajorAttendLib()
use sru_library;

#DROP PROCEDURE GetAttendDetails;

Create Procedure GetAttendDetails()
BEGIN
    SELECT s.student_id as student_id,
           s.student_name as studentName,
           s.gender as gender,
           m.major_name as majorName,
           at.entry_times as entryTimes,
           at.exiting_times as exitingTime,
           at.purpose as purpose
           From attend at
               INNER JOIN students s ON s.student_id = at.student_id
               INNER JOIN majors m ON s.major_id = m.major_id
    WHERE at.date = CURDATE();
end;

CALL GetAttendDetails()
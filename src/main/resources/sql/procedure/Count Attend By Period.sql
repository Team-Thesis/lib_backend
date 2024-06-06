USE sru_library;
# Get borrow book
Create PROCEDURE CountAttendByPeriod(in inputDate DATE, in period INT)
BEGIN
    DECLARE current_date_count INT;
    DECLARE previous_date DATE;
    DECLARE previous_date_count INT;
    DECLARE current_7_day_count INT;
    DECLARE previous_7_day_count INT;
    DECLARE current_month_count INT;
    DECLARE previous_month_count INT;
    DECLARE current_year_count INT;
    DECLARE previous_year_count INT;

    SET previous_date = inputDate - interval 1 day;

    IF period = 1 Then
        # Select current date
        SELECT COUNT(*) INTO current_date_count
        FROM attend WHERE date = inputDate;
        # Select previous day
        SELECT COUNT(*) INTO previous_date_count
        FROM attend WHERE date = previous_date;
        # Return the results
        SELECT current_date_count as current_day,
               previous_date_count as previous_day;

    ELSEIF period = 7 Then
        #Select current week
        SELECT COUNT(*) INTO current_7_day_count
        FROM attend
        WHERE date BETWEEN inputDate - INTERVAL 6 day and inputDate;
        # Select previous week
        SELECT COUNT(*) INTO previous_7_day_count
        FROM attend
        WHERE date BETWEEN inputDate - INTERVAL 13 day and inputDate - INTERVAL 7 day;
        # Return the results
        SELECT current_7_day_count as current_7_days,
               previous_7_day_count as previous_7_days;

    ELSEIF period = 30 Then
        # Select count for current month
        SELECT COUNT(*) INTO current_month_count
        FROM attend
        WHERE YEAR(date) = YEAR(inputDate) AND MONTH(date) = MONTH(inputDate);
        # Select count for previous month
        SELECT COUNT(*) INTO previous_month_count
        FROM attend
        WHERE YEAR(date) = YEAR(inputDate - INTERVAL 1 MONTH) AND MONTH(date) = MONTH(inputDate - INTERVAL 1 MONTH);
        # Return Value
        SELECT current_month_count as current_month,
               previous_month_count as previous_month;

    ELSEIF period = 365 Then
        # Select count for current year
        SELECT COUNT(*) INTO current_year_count
        FROM attend
        WHERE date BETWEEN inputDate - INTERVAL 364 day and inputDate;
        # Select count for previous year
        SELECT COUNT(*) INTO previous_year_count
        FROM attend
        WHERE date between inputDate - INTERVAL ((365*2) - 1) day and inputDate - Interval 365 day ;
        # Return Value
        SELECT current_year_count as current_year,
               previous_year_count as previous_year;
    END if;
end;

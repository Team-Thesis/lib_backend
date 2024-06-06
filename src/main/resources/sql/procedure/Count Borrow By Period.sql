USE sru_library;
# Get borrow book
Create PROCEDURE CountBorrowByPeriod(in date DATE, in period INT)
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

    SET previous_date = date - interval 1 day;

    IF period = 1 Then
        # Select current date
        SELECT COUNT(*) INTO current_date_count
        FROM borrow_books WHERE borrow_date = date;
        # Select previous day
        SELECT COUNT(*) INTO previous_date_count
        FROM borrow_books WHERE borrow_date = previous_date;
        # Return the results
        SELECT current_date_count as current_day,
               previous_date_count as previous_day;

    ELSEIF period = 7 Then
        #Select current week
        SELECT COUNT(*) INTO current_7_day_count
        FROM borrow_books
        WHERE borrow_date BETWEEN date - INTERVAL 6 day and date;
        # Select previous week
        SELECT COUNT(*) INTO previous_7_day_count
        FROM borrow_books
        WHERE borrow_date BETWEEN date - INTERVAL 13 day and date - INTERVAL 7 day;
        # Return the results
        SELECT current_7_day_count as current_7_days,
               previous_7_day_count as previous_7_days;

    ELSEIF period = 30 Then
        # Select count for current month
        SELECT COUNT(*) INTO current_month_count
        FROM borrow_books
        WHERE YEAR(borrow_date) = YEAR(date) AND MONTH(borrow_date) = MONTH(date);
        # Select count for previous month
        SELECT COUNT(*) INTO previous_month_count
        FROM borrow_books
        WHERE YEAR(borrow_date) = YEAR(date - INTERVAL 1 MONTH) AND MONTH(borrow_date) = MONTH(date - INTERVAL 1 MONTH);
        # Return Value
        SELECT current_month_count as current_month,
               previous_month_count as previous_month;
    ELSEIF period = 365 Then
        # Select count for current year
        SELECT COUNT(*) INTO current_year_count
        FROM borrow_books
        WHERE borrow_date BETWEEN date - INTERVAL 364 day and date;
        # Select count for previous year
        SELECT COUNT(*) INTO previous_year_count
        FROM borrow_books
        WHERE borrow_date between date - INTERVAL ((365*2) - 1) day and date - Interval 365 day ;
        # Return Value
        SELECT current_year_count as current_year,
               previous_year_count as previous_year;
    END if;
end;

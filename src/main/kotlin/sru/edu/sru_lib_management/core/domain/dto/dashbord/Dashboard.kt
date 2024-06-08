package sru.edu.sru_lib_management.core.domain.dto.dashbord

data class Dashboard(
    val entryToday: CustomEntryCount,
    val customBorrow: CustomBorrowCount,
    val bookSponsor: BookSponsor,
    val customEntry: List<StudentEntry>,
    val totalBookOfThisMonth: TotalBookOfThisMonth,
    val totalEntryThisMonth: TotalEntryThisMonth,
    val totalMajorVisitor: TotalMajorVisitor,
    val weeklyVisitor: WeeklyVisitor
)
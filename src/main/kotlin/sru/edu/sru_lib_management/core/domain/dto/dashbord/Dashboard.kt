package sru.edu.sru_lib_management.core.domain.dto.dashbord

data class Dashboard(
    val cardData: List<CardData>,
    val totalMajorVisitor: Any,
    val weeklyVisitor: Any,
    val bookAvailable: Any,
    val customEntry: Any
)
package sru.edu.sru_lib_management.core.domain.dto.dashbord

data class TotalMajorVisitor(
    val total: Int,
    val majorCount: Map<String, Int>
)
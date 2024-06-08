package sru.edu.sru_lib_management.core.domain.service

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.dashbord.CustomBorrowCount
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import java.time.LocalDate

@Service
interface IBorrowService {
    // CRUD
    suspend fun saveBorrow(borrowBook: BorrowBook): Result<BorrowBook>
    suspend fun updateBorrow(borrowBook: BorrowBook): Result<BorrowBook>
    suspend fun getBorrow(borrowID: Long): Result<BorrowBook?>
    fun getBorrows(): Result<Flow<BorrowBook>>
    suspend fun deleteBorrow(borrowID: Long): Result<Boolean>
    //

    suspend fun countBorrowPerWeek(): Result<Map<LocalDate, Int>>
    suspend fun compareCurrentAndPreviousBorrow(date: LocalDate, period: Int): Result<CustomBorrowCount>
}

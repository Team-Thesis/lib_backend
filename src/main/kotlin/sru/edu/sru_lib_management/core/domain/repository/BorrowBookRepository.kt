package sru.edu.sru_lib_management.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository
import java.sql.Date

@Repository
interface BorrowBookRepository : ICrudRepository<BorrowBook, Long> {
    fun customBorrow(date: Date): Flow<BorrowBook>
}

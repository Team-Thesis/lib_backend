package sru.edu.sru_lib_management.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.DELETE_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.GET_BORROW_BOOKS_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.GET_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.SAVE_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.UPDATE_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import sru.edu.sru_lib_management.core.domain.repository.BorrowBookRepository
import java.sql.Date

@Component
class BorrowBookRepositoryImp(
    val client: DatabaseClient
) : BorrowBookRepository {
    override fun customBorrow(date: Date): Flow<BorrowBook> {
        TODO("Not yet implemented")
    }

    override suspend fun save(data: BorrowBook) {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: BorrowBook) {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): BorrowBook? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<BorrowBook> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("Not yet implemented")
    }

}

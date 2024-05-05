package sru.edu.sru_lib_management.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.DELETE_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.GET_BORROW_BOOKS_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.GET_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.SAVE_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowBookQuery.UPDATE_BORROW_BOOK_QUERY
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import sru.edu.sru_lib_management.core.domain.repository.BorrowBookRepository
import sru.edu.sru_lib_management.core.util.APIException
import java.sql.Date

@Component
class BorrowBookRepositoryImp(
    val client: DatabaseClient
) : BorrowBookRepository {

    override fun customBorrow(date: Date): Collection<BorrowBook> {
        TODO("Not yet implemented")
    }

    override suspend fun save(data: BorrowBook) {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: BorrowBook): BorrowBook {
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

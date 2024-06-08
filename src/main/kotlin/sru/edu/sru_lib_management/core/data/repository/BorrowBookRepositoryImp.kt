package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.data.query.BorrowQuery.DELETE_BORROW_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowQuery.GET_BORROWS_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowQuery.GET_BORROW_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowQuery.SAVE_BORROW_QUERY
import sru.edu.sru_lib_management.core.data.query.BorrowQuery.UPDATE_BORROW_QUERY
import sru.edu.sru_lib_management.core.domain.dto.CompareValue
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import sru.edu.sru_lib_management.core.domain.repository.book_repository.BorrowBookRepository
import java.sql.Date
import java.time.LocalDate

@Component
class BorrowBookRepositoryImp(
    private val client: DatabaseClient
) : BorrowBookRepository {
    override fun customBorrow(date: Date): Flow<BorrowBook> {
        TODO("Not yet implemented")
    }

    override suspend fun countBorrowPerWeek(): Map<LocalDate, Int> {
       return client.sql("CALL CountBorrowPerWeek()")
           .map { row ->
               val date = row.get("borrow_date", LocalDate::class.java)!!
               val count = row.get("count", Int::class.java)!!
               date to count
           }
           .all()
           .collectList()
           .awaitSingle()
           .toMap()
    }

    override suspend fun countCurrentAndPreviousBorrow(
        date: LocalDate, period: Int
    ): CompareValue {
        val param = mapOf(
            "date" to date,
            "period" to period
        )
        return client.sql("CALL CountBorrowByPeriod(:date, :period)")
            .bindValues(param)
            .map { row ->
                CompareValue(
                    row.get("current_value", Int::class.java)!!,
                    row.get("previous_value", Int::class.java)!!
                )
            }
            .one()
            .awaitSingle()
    }

    override suspend fun save(entity: BorrowBook): BorrowBook {
        client.sql(SAVE_BORROW_QUERY)
            .bindValues(paramsMap(entity))
            .await()
        return entity
    }

    override suspend fun update(entity: BorrowBook): BorrowBook {
        client.sql(UPDATE_BORROW_QUERY)
            .bind("borrow_id", entity.borrowId)
            .bindValues(paramsMap(entity))
            .fetch()
            .awaitRowsUpdated()
        return entity
    }

    override suspend fun getById(id: Long): BorrowBook? = client.sql(GET_BORROW_QUERY)
            .bind("borrowId", id)
            .map { row: Row, _ ->
                row.rowMapping()
            }
            .awaitOneOrNull()

    override fun getAll(): Flow<BorrowBook> = client
        .sql(GET_BORROWS_QUERY)
        .map { row: Row, _ ->
            row.rowMapping()
        }
        .flow()


    override suspend fun delete(id: Long): Boolean {
        val rowEffected = client.sql(DELETE_BORROW_QUERY)
            .bind("borrow_id", id)
            .fetch()
            .awaitRowsUpdated()
        return rowEffected > 0
    }

    private fun Row.rowMapping(): BorrowBook = BorrowBook(
        borrowId = this.get("borrow_id", Long::class.java)!!,
        bookId = this.get("book_id", String::class.java)!!,
        studentId = this.get("student_id", Long::class.java)!!,
        borrowDate = this.get("borrow_date", LocalDate::class.java)!!,
        giveBackDate = this.get("give_back_date", LocalDate::class.java)!!,
        isBringBack = this.get("is_bring_back", Boolean::class.java)!!
    )

    private fun paramsMap(borrow: BorrowBook): Map<String, Any> = mapOf(
        "bookId" to borrow.bookId,
        "studentId" to borrow.studentId,
        "borrowDate" to borrow.borrowDate,
        "giveBackDate" to borrow.giveBackDate,
        "isBringBack" to borrow.isBringBack
    )
}

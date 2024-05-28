package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import sru.edu.sru_lib_management.core.data.query.BookQuery.DELETE_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.GET_BOOKS_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.GET_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.SAVE_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.SEARCH_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.UPDATE_BOOK_QUERY
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.repository.book_repository.BookRepository


@Component
class BookRepositoryImp(
    private val client: DatabaseClient
) : BookRepository {

   suspend fun searchBooks(criteria: Map<String, Any?>): List<Books> {
        val query = StringBuilder(SEARCH_BOOK_QUERY)
        val params = mutableMapOf<String, Any?>()

        criteria.forEach { (k, v) ->
            if (v != null){
                query.append("AND $k = :$k")
                params[k] = v
            }
        }
        println(query.toString())
        println()
        val execute = client.sql(query.toString())
        params.forEach {(k,v) ->
            execute.bind(k, v)
        }
        return execute.map { row: Row, _ ->
            row.rowMapping()
        }.all().collectList().awaitSingle()
    }

    @Transactional
    override suspend fun save(entity: Books): Books {
        client.sql(SAVE_BOOK_QUERY)
            .bindValues(paramMap(entity))
            .await()
        return entity
    }

    override suspend fun update(entity: Books): Books {
        client.sql(UPDATE_BOOK_QUERY)
            .bindValues(paramMap(entity))
            .fetch()
            .awaitRowsUpdated()
        return entity
    }

    override suspend fun getById(id: String): Books? {
        return client.sql(GET_BOOK_QUERY)
            .bind("bookId", id)
            .map { row: Row, _ ->
                row.rowMapping()
            }
            .awaitOneOrNull()
    }

    override fun getAll(): Flow<Books> {
        return client.sql(GET_BOOKS_QUERY)
            .map { row: Row, _ ->
                row.rowMapping()
            }.flow()
    }

    override suspend fun delete(id: String): Boolean {
        val rowEffect = client.sql(DELETE_BOOK_QUERY)
            .bind("bookId", id)
            .fetch()
            .awaitRowsUpdated()
        return rowEffect > 0
    }

    private fun Row.rowMapping(): Books = Books(
        this.get("book_id", String::class.java)!!,
        this.get("book_title", String::class.java)!!,
        this.get("number", Int::class.java)!!,
        this.get("language_id", String::class.java)!!,
        this.get("college_id", String::class.java)!!,
        this.get("book_type", String::class.java)!!,
    )

    private fun paramMap(books: Books): Map<String, Any?>{
        return mapOf(
            "bookId" to books.bookId,
            "bookTitle" to books.bookTitle,
            "number" to books.number,
            "languageId" to books.languageId,
            "collegeId" to books.collegeId,
            "bookType" to books.bookType
        )
    }
}

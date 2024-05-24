package sru.edu.sru_lib_management.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.bind
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import sru.edu.sru_lib_management.core.data.query.BookQuery.GET_BOOKS_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.SAVE_BOOK_QUERY
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.repository.BookRepository


@Component
class BookRepositoryImp(
    private val client: DatabaseClient
) : BookRepository {

    @Transactional
    override suspend fun save(entity: Books): Books {
        client.sql(SAVE_BOOK_QUERY)
            .bindValues(paramMap(entity))
            .await()
        return entity
    }

    override suspend fun update(entity: Books): Books {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): Books? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<Books> {
        return client.sql(GET_BOOKS_QUERY)
            .map { row ->
                Books(
                    row.get("book_id", String::class.java),
                    row.get("book_title", String::class.java)!!,
                    row.get("number", Int::class.java)!!,
                    row.get("sponsor_id" , Int::class.java)!!,
                    row.get("language_id", Int::class.java)!!,
                    row.get("college_id", Int::class.java)!!,
                    row.get("book_type", String::class.java)!!,
                )
            }.flow()
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    private fun paramMap(books: Books): Map<String, Any>{
        return mapOf(
            "bookID" to books.bookID!!,
            "bookTitle" to books.bookTitle,
            "number" to books.number,
            "sponsorID" to books.sponsorID,
            "languageID" to books.languageID,
            "collegeID" to books.collegeID,
            "bookType" to books.bookType
        )
    }
}

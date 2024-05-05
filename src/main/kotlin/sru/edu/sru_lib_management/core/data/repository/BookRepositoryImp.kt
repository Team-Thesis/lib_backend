package sru.edu.sru_lib_management.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.data.query.BookQuery.GET_BOOKS_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.GET_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.SAVE_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.BookQuery.UPDATE_BOOK_QUERY
import sru.edu.sru_lib_management.core.data.query.StudentQuery.GET_STUDENTS_QUERY
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.model.Students
import sru.edu.sru_lib_management.core.domain.repository.BookRepository

@Component
class BookRepositoryImp(
    private val client: DatabaseClient
) : BookRepository {
    override suspend fun save(data: Books) {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: Books): Books {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): Books? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<Books> {
        return client.sql(GET_BOOKS_QUERY)
            .map { row ->
                Books(
                    row.get("book_id", Long::class.java),
                    row.get("book_title", String::class.java)!!,
                    row.get("language_id", Int::class.java)!!,
                    row.get("college_id", Int::class.java)!!,
                    row.get("book_type", String::class.java)!!,
                )
            }.flow()
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("Not yet implemented")
    }


}

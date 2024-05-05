package sru.edu.sru_lib_management.core.domain.service.bookService

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.repository.BookRepository

@Service
class BookService(
    @Qualifier("bookRepositoryImp") private val repository: BookRepository
): IBookService {
    override fun saveBook(books: Books?): Books? {
        TODO("Not yet implemented")
    }

    override fun updateBook(books: Books?): Books? {
        TODO("Not yet implemented")
    }

    override fun getBook(bookID: Long?): Books? {
        TODO("Not yet implemented")
    }

    override fun getAllBooks(): Flow<Books> {
        return repository.getAll()
    }

    override fun deleteBook(bookID: Long?): Boolean? {
        TODO("Not yet implemented")
    }
}
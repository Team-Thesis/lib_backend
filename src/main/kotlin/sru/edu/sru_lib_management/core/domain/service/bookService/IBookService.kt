package sru.edu.sru_lib_management.core.domain.service.bookService

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Books

interface IBookService {
    fun saveBook(books: Books?): Books?
    fun updateBook(books: Books?): Books?
    fun getBook(bookID: Long?): Books?
    fun getAllBooks(): Flow<Books>
    fun deleteBook(bookID: Long?): Boolean?
}

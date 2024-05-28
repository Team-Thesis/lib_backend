package sru.edu.sru_lib_management.core.domain.service

import kotlinx.coroutines.flow.Flow
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.common.Result

interface IBookService {
    fun getAllBooks(): Result<Flow<Books>>
    suspend fun saveBook(books: Books): Result<Books?>
    suspend fun updateBook(books: Books): Result<Books?>
    suspend fun getBook(bookID: Long): Result<Books?>
    suspend fun deleteBook(bookID: Long): Result<Boolean?>
}

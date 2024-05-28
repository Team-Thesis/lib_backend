package sru.edu.sru_lib_management.core.domain.service.implementation

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.repository.book_repository.BookRepository
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.service.IBookService

@Service
class BookService(
    @Qualifier("bookRepositoryImp") private val repository: BookRepository
): IBookService {
    override fun getAllBooks(): Result<Flow<Books>> {
        return runCatching {
            repository.getAll()
        }.fold(
            onSuccess = {Result.Success(it)},
            onFailure = {e->
                println(e.printStackTrace())
                Result.Failure("${e.message}")
            }
        )
    }

    override suspend fun saveBook(books: Books): Result<Books?> {
        return runCatching {
            val areFieldsBlank = books.bookId.isBlank() || books.bookTitle.isBlank()
            val isHasID = repository.getById(books.bookId)
            if (isHasID != null || areFieldsBlank){
                return Result.ClientError("Something went wrong!")
            }
            repository.save(entity = books)
        }.fold(
            onSuccess = {result->
                Result.Success(result)
            },
            onFailure = {e ->
                println(e.printStackTrace())
                Result.Failure("${e.message}")
            }
        )
    }

    override suspend fun updateBook(books: Books): Result<Books?> {
        return runCatching {
            repository.getById(books.bookId) ?: return Result.ClientError("Book with ID ${books.bookId} not found!")
            repository.update(books)
        }.fold(
            onSuccess = {data ->
                Result.Success(data)
            },
            onFailure = {e ->
                println(e.printStackTrace())
                Result.Failure("${e.message}")
            }
        )
    }

    override suspend fun getBook(bookID: Long): Result<Books?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBook(bookID: Long): Result<Boolean?> {
        TODO("Not yet implemented")
    }

}
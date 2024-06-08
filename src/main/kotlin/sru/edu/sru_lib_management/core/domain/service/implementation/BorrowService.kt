package sru.edu.sru_lib_management.core.domain.service.implementation

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.dto.dashbord.CustomBorrowCount
import sru.edu.sru_lib_management.core.domain.model.BorrowBook
import sru.edu.sru_lib_management.core.domain.repository.book_repository.BookRepository
import sru.edu.sru_lib_management.core.domain.repository.book_repository.BorrowBookRepository
import sru.edu.sru_lib_management.core.domain.repository.student_repository.StudentRepository
import sru.edu.sru_lib_management.core.domain.service.IBorrowService
import java.time.LocalDate

@Component
class BorrowService(
    @Qualifier("borrowBookRepositoryImp") private val borrowBookRepository: BorrowBookRepository,
    private val bookRepository: BookRepository,
    private val studentRepository: StudentRepository
) : IBorrowService {
    override suspend fun saveBorrow(
        borrowBook: BorrowBook
    ): Result<BorrowBook> = runCatching {
        if (borrowBook.bookId.isBlank()){
            return Result.ClientError("Field cannot be blank.")
        }
        bookRepository.getById(borrowBook.bookId)
            ?: return Result.ClientError("Not found book with this ID: ${borrowBook.bookId}.")
        studentRepository.getById(borrowBook.studentId)
            ?: return Result.ClientError("Not found student with this ID: ${borrowBook.studentId}.")
        borrowBookRepository.save(borrowBook)
    }.fold(
        onSuccess = {data ->
            Result.Success(data)
        },
        onFailure = {e ->
            println(e.printStackTrace())
            Result.Failure(e.message.toString())
        }
    )

    override suspend fun updateBorrow(
        borrowBook: BorrowBook
    ): Result<BorrowBook> = runCatching{
        if (borrowBook.borrowId == null)
            return Result.ClientError("Please enter id for update")
        borrowBookRepository.getById(borrowBook.borrowId) ?: return Result.ClientError("Not found borrowing with this ID: ${borrowBook.borrowId}")
        borrowBookRepository.update(borrowBook)
    }.fold(
        onSuccess = {data ->
            Result.Success(data)
        },
        onFailure = {
            println(it.printStackTrace())
            Result.Failure(it.message.toString())
        }
    )

    override suspend fun getBorrow(
        borrowID: Long
    ): Result<BorrowBook?> = runCatching {
        borrowBookRepository.getById(borrowID) ?: return Result.ClientError("Not found borrowing with this ID: $borrowID")
    }.fold(
        onSuccess = {
            Result.Success(it)
        },
        onFailure = {
            println(it.printStackTrace())
            Result.Failure(it.message.toString())
        }
    )

    override fun getBorrows(): Result<Flow<BorrowBook>> = runCatching {
        borrowBookRepository.getAll()
    }.fold(
        onSuccess = {Result.Success(it)},
        onFailure = {
            println(it.message)
            Result.Failure(it.message.toString())
        }
    )

    override suspend fun deleteBorrow(borrowID: Long): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun countBorrowPerWeek(): Result<Map<LocalDate, Int>> = runCatching {
        borrowBookRepository.countBorrowPerWeek()
    }.fold(
        onSuccess = {
            Result.Success(it)
        },
        onFailure = {
            println(it.printStackTrace())
            Result.Failure(it.message.toString())
        }
    )

    override suspend fun compareCurrentAndPreviousBorrow(
        date: LocalDate,
        period: Int
    ): Result<CustomBorrowCount> = runCatching {

        val areFieldBlank = period == 0 || date > LocalDate.now()
        val writeInput = period == 1 || period == 7 || period == 30 || period == 365

        if (areFieldBlank || !writeInput)
            return Result.ClientError("Invalid data input.")

        val getCount = borrowBookRepository.countCurrentAndPreviousBorrow(date, period)
        val currentCount = getCount.currentValue
        val previousCount = getCount.previousValue
        val percentageChange: Float = if (previousCount == 0){
            if (currentCount == 0) 0f else 100f
        }else{
            ((currentCount - previousCount)).toFloat() / previousCount * 100f
        }
        CustomBorrowCount(
            currentCount,
            percentageChange
        )
    }.fold(
        onSuccess = {
            value ->  Result.Success(value)
        },
        onFailure = { e ->
            println(e.printStackTrace())
            Result.Failure(e.message.toString())
        }
    )
}
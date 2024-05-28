package sru.edu.sru_lib_management.core.domain.service


interface IBorrowService<T> {
    fun saveBorrow(borrowBook: T): T
    fun updateBorrow(borrowBook: T): T
    fun getBorrow(borrowID: Long): T?
    fun getBorrows(): Collection<T>
    fun deleteBorrow(borrowID: Long): Boolean
}

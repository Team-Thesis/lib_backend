package sru.edu.sru_lib_management.core.domain.repository.book_repository

import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.core.domain.model.Books
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository

@Repository
interface BookRepository : ICrudRepository<Books, String>
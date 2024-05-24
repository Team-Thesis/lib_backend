package sru.edu.sru_lib_management.core.domain.repository

import sru.edu.sru_lib_management.core.domain.model.BookSponsor
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository
import java.sql.Date

interface BookSponsorRepository : ICrudRepository<BookSponsor, Int>{
    suspend fun count(date: Date): Int
}
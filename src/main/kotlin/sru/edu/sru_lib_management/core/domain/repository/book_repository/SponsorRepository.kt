package sru.edu.sru_lib_management.core.domain.repository.book_repository

import sru.edu.sru_lib_management.core.domain.model.Sponsor
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository
import java.sql.Date

interface SponsorRepository : ICrudRepository<Sponsor, Int>
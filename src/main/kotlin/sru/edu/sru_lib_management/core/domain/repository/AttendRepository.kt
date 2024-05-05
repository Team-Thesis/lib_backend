package sru.edu.sru_lib_management.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.crud.ICrudRepository
import java.sql.Date

@Repository
interface AttendRepository : ICrudRepository<Attend, Long> {
    suspend fun getCustomAttend(date: Date): Flow<Attend>
}

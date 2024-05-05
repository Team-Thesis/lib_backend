package sru.edu.sru_lib_management.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import sru.edu.sru_lib_management.core.data.query.AttendQuery.DELETE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.GET_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.SAVE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.data.query.AttendQuery.UPDATE_ATTEND_QUERY
import sru.edu.sru_lib_management.core.domain.model.Attend
import sru.edu.sru_lib_management.core.domain.repository.AttendRepository
import sru.edu.sru_lib_management.core.util.APIException
import sru.edu.sru_lib_management.core.util.SaveCallBack
import java.sql.Date

@Component
class AttendRepositoryImp(
    private val client: DatabaseClient
) : AttendRepository {

    override suspend fun getCustomAttend(date: Date): Flow<Attend> {
        TODO("Not yet implemented")
    }

    override suspend fun save(data: Attend, callBack: SaveCallBack) {
        TODO("Not yet implemented")
    }

    override suspend fun update(data: Attend): Attend {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): Attend? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<Attend> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("Not yet implemented")
    }


}

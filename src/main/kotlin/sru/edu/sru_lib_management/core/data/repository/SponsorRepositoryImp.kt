package sru.edu.sru_lib_management.core.data.repository

import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.ExecuteFunction
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.stereotype.Component
import sru.edu.sru_lib_management.core.domain.model.Sponsor
import sru.edu.sru_lib_management.core.domain.repository.book_repository.SponsorRepository
import sru.edu.sru_lib_management.core.data.query.BookQuery.SAVE_SPONSOR_QR
import sru.edu.sru_lib_management.core.data.query.BookQuery.UPDATE_SPONSOR_QR

@Component
class SponsorRepositoryImp(
    private val client: DatabaseClient
) : SponsorRepository {

    override suspend fun save(entity: Sponsor): Sponsor {
        val save = client.sql(SAVE_SPONSOR_QR)
            .filter{s: Statement, next: ExecuteFunction ->
                next.execute(s.returnGeneratedValues("sponsor_id"))
            }
            .bindValues(paramsMap(entity))
            .await()
        println(save)
        return entity
    }

    override suspend fun update(entity: Sponsor): Sponsor {
        client.sql(UPDATE_SPONSOR_QR)
            .bindValues(paramsMap(entity))
            .fetch()
            .awaitRowsUpdated()
        return entity
    }

    override suspend fun getById(id: Int): Sponsor? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<Sponsor> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    private fun Row.rowMapping(): Sponsor = Sponsor(
        this.get("sponsorId", Int::class.java),
        this.get("sponsor_name", String::class.java)!!
    )

    private fun paramsMap(bookSponsor: Sponsor): Map<String, Any?> = mapOf(
        "sponsorId" to bookSponsor.sponsorId,
        "sponsor_name" to bookSponsor.sponsorName
    )
}
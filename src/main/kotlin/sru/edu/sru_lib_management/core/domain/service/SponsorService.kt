package sru.edu.sru_lib_management.core.domain.service

import sru.edu.sru_lib_management.core.common.Result
import sru.edu.sru_lib_management.core.domain.model.Sponsor

abstract class SponsorService : IStudentService {
    abstract suspend fun saveSponsor(sponsor: Sponsor): Result<Sponsor>
}
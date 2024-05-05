package sru.edu.sru_lib_management.core.domain.model

import java.sql.Timestamp
import java.util.*

data class Attend (
    var attendID: Long?,
    var studentID: Long,
    var entryTimes: Timestamp,
    var exitingTimes: Timestamp,
    var date: Date,
    var purpose: String
)

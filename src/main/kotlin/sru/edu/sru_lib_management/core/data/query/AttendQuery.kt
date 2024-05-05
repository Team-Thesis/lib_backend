package sru.edu.sru_lib_management.core.data.query

object AttendQuery {
    const val SAVE_ATTEND_QUERY: String = "INSERT INTO attend (student_id, entry_times, exiting_times, date, purpose)" +
            "VALUES( :studentID, :entryTimes, :exitingTime, :date, :purpose);"
    const val UPDATE_ATTEND_QUERY: String = "UPDATE attend set student_id = :studentID, entry_times = :entryTimes" +
            "exiting_times = :exitingTimes, date = :date, purpose = :purpose;"
    const val DELETE_ATTEND_QUERY: String = "DELETE FROM attend WHERE attend_id = :attendID;"
    const val GET_ATTEND_QUERY: String = "SELECT * FROM attend WHERE attend_id = :attendID;"
    const val GET_ALL_ATTEND_QUERY: String = "SELECT * FROM attend;"
}


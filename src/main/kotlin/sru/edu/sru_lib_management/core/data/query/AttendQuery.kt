package sru.edu.sru_lib_management.core.data.query

object AttendQuery {
    const val SAVE_ATTEND_QUERY = "INSERT INTO attend (student_id, entry_times, purpose, date)" +
            "VALUES( :studentID, :entryTimes, :purpose, :date);"
    const val UPDATE_ATTEND_QUERY = "UPDATE attend set student_id = :studentID, entry_times = :entryTimes" +
            "exiting_times = :exitingTimes, date = :date, purpose = :purpose;"
    const val DELETE_ATTEND_QUERY = "DELETE FROM attend WHERE attend_id = :attendID;"
    const val GET_ATTEND_QUERY = "SELECT * FROM attend WHERE attend_id = :attendID;"
    const val GET_ATTEND_QUERY_BY_STUDENT_ID = "SELECT * FROM attend WHERE student_id = :studentID and date = :date"
    const val GET_ALL_ATTEND_QUERY = "SELECT * FROM attend;"

    const val UPDATE_EXIT_TIME = "UPDATE attend SET exiting_times = :exitingTimes WHERE student_id = :studentID AND date = :date"

}


package sru.edu.sru_lib_management.core.data.query

object StudentQuery {
    const val SAVE_STUDENT_QUERY =
        "Insert into students (student_id, student_name, gender, date_of_birth, degree_level_id, major_id, generation) " +
                "VALUES (:studentId, :studentName, :gender, :dateOfBirth, :degreeLevelId, :majorId, :generation);"

    const val UPDATE_STUDENT_QUERY =
        "UPDATE students SET student_name = :studentName, gender = :gender, date_of_birth = :dateOfBirth, " +
                "degree_level_id = :degreeLevelId, major_id = :majorId, generation = :generation WHERE student_id = :studentId;"

    const val DELETE_STUDENT_QUERY = "DELETE FROM students WHERE student_id = :studentId;"

    const val GET_STUDENT_QUERY = "SELECT * FROM students WHERE student_id = :studentId;"

    const val GET_STUDENTS_QUERY = "SELECT * FROM students;"
}

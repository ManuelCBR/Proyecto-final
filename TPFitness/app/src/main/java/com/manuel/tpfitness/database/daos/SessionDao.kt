package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manuel.tpfitness.database.entities.FullSession

import com.manuel.tpfitness.database.entities.SessionEntity

@Dao
interface SessionDao {

    @Query("SELECT * FROM entrenamiento")
    suspend fun getSession(): MutableList<SessionEntity>
    @Query("SELECT date FROM entrenamiento")
    suspend fun getDatesSession(): MutableList<String>

    @Query("SELECT MAX(id_session) FROM entrenamiento")
    suspend fun getLastId(): Int
    @Query("SELECT date FROM entrenamiento WHERE id_session=:id")
    suspend fun dateSessionById(id: Int): String
    @Query("SELECT id_session FROM entrenamiento WHERE date=:date")
    suspend fun sessionByDate(date: String): Int
    @Query("SELECT en.id_session, en.name_session, en.date," +
            "ser.id_exercise as idExercise, j.name_exercise as nameExercise," +
            " ser.id_serie as idSerie, ser.weight, ser.reps FROM ejercicios_entrenamiento as e \n" +
            "natural join ejercicios as j\n" +
            "natural join entrenamiento as en\n" +
            "natural join series as ser\n" +
            "where j.id_exercise = e.id_exercise_session\n" +
            "and en.id_session = e.id_session_session\n" +
            "and ser.id_session = e.id_session_session\n" +
            "and ser.id_exercise = j.id_exercise\n" +
            "and en.date=:date /*ORDER BY id_session, id_exercise, id_serie*/"
    )
    suspend fun getFullSession(date: String): MutableList<FullSession>

    @Insert
    suspend fun addSession(session: SessionEntity)

    @Update
    suspend fun updateSession(session: SessionEntity)

    @Query("DELETE FROM entrenamiento WHERE date=:date")
    suspend fun delSession(date: String)
    @Query("DELETE FROM entrenamiento WHERE id_session=:id")
    suspend fun delSessionById(id: Int)
    
}
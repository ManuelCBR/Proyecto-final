package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manuel.tpfitness.database.entities.ExercisesSessionEntity

@Dao
interface ExercisesSessionDao {
    @Query("SELECT * FROM ejercicios_entrenamiento")
    suspend fun getExerciseSession(): MutableList<ExercisesSessionEntity>
    @Query("SELECT id_exercise_session FROM ejercicios_entrenamiento WHERE id_session_session=:id")
    suspend fun getNumExerciseByIdSession(id: Int): MutableList<Int>

    @Query("SELECT MAX(id_session_session) FROM ejercicios_entrenamiento")
    suspend fun getLastId(): Int
    @Query("SELECT MAX(id_exercise_session) FROM ejercicios_entrenamiento")
    suspend fun getLastExerciseId(): Int
    @Query("SELECT id_exercise_session FROM ejercicios_entrenamiento")
    suspend fun getIdExercise(): MutableList<Int>
    @Query("SELECT id_exercise_session FROM ejercicios_entrenamiento")
    suspend fun getIdSession(): MutableList<Int>
    @Query("SELECT id_session_session FROM ejercicios_entrenamiento WHERE id_exercise_session=:id limit 1")
    suspend fun getIdSessionByIdExercise(id: Int): Int
    @Insert
    suspend fun addExerciseSession(exerciseSession: ExercisesSessionEntity)

    @Update
    suspend fun updateExerciseSession(exerciseSession: ExercisesSessionEntity)

    @Query("DELETE FROM ejercicios_entrenamiento WHERE id_session_session=:idSession AND id_exercise_session=:idExercise")
    suspend fun delExerciseSession(idSession: Int, idExercise: Int)
}
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
    @Query("SELECT MAX(id_session_session) FROM ejercicios_entrenamiento")
    suspend fun getLastId(): Int
    @Insert
    suspend fun addExerciseSession(exerciseSession: ExercisesSessionEntity)

    @Update
    suspend fun updateExerciseSession(exerciseSession: ExercisesSessionEntity)

    @Query("DELETE FROM ejercicios_entrenamiento WHERE id_session_session=:id")
    suspend fun delExerciseSession(id: Int)
}
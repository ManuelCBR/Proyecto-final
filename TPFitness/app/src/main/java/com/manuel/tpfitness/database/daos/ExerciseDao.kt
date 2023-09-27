package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manuel.tpfitness.database.entities.ExerciseEntity

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM ejercicios")
    suspend fun getExercises(): MutableList<ExerciseEntity>

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM ejercicios WHERE id_exercise=:id")
    suspend fun delExercise(id: Int)

}
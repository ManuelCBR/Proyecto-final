package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manuel.tpfitness.database.entities.ExerciseEntity

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM ejercicios")
    suspend fun getExercises(): MutableList<ExerciseEntity>

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity)

    @Query("UPDATE ejercicios SET name_exercise=:name, description_exercise=:description WHERE id_exercise=:id")
    suspend fun updateExercise(name: String, description: String, id: Int)

    @Query("DELETE FROM ejercicios WHERE id_exercise=:id")
    suspend fun delExercise(id: Int)

}
package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.manuel.tpfitness.database.entities.ExerciseMuscleEntity

@Dao
interface ExerciseMuscleDao {
    @Transaction
    @Query("SELECT * FROM ejercicios")
    suspend fun getAllFromExerciseMuscle(): MutableList<ExerciseMuscleEntity>

    @Query("SELECT * FROM ejercicios WHERE id_muscle_group_table=:id")
    suspend fun getExerciseById(id: Int): MutableList<ExerciseMuscleEntity>
    @Query("SELECT name_group FROM grupos_musculares")
    suspend fun getNameMuscleGroup(): MutableList<String>
}
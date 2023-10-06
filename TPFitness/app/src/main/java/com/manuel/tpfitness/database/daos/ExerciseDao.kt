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
    @Query("SELECT * FROM ejercicios WHERE id_muscle_group_table=:id")
    suspend fun getExerciseById(id: Int): MutableList<ExerciseEntity>
    @Query("SELECT id_exercise FROM ejercicios WHERE Upper(name_exercise)=Upper(:name)")
    suspend fun getNameExercisesById(name: String): Int

    @Insert
    suspend fun addExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM ejercicios WHERE id_exercise=:id")
    suspend fun delExercise(id: Int)

}
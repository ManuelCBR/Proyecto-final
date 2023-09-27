package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manuel.tpfitness.database.entities.ExerciseEntity
import com.manuel.tpfitness.database.entities.MuscleGroupEntity

@Dao
interface MuscleGroupDao {
    @Query("SELECT * FROM grupos_musculares")
    suspend fun getAllMuscleGroup(): MutableList<MuscleGroupEntity>
    @Query("SELECT * FROM grupos_musculares WHERE id_muscle_group=:id")
    suspend fun getMuscleGroupById(id: Int): MuscleGroupEntity

    @Insert
    suspend fun addMuscleGroup(muscleGruop: MuscleGroupEntity)

}
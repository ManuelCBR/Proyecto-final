package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "ejercicios")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id_exercise") val idExercise: Int,
    @ColumnInfo (name = "name_exercise") val nameExercise: String,
    @ColumnInfo (name = "description_exercise") val descriptionExercise: String,
    @Embedded val muscleGroupExercise: MuscleGroupEntity

)
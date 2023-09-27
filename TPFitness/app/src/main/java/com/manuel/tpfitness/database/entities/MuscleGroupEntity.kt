package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (tableName = "grupos_musculares")
data class MuscleGroupEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_muscle_group") val idMuscleGroup: Int,
    @ColumnInfo(name = "name_group") val nameMuscleGroup: String,
    @ColumnInfo(name = "description_group") val descriptionMuscleGroup: String
)
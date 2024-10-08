package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "series",
    primaryKeys = ["id_session", "id_exercise", "id_serie"],
    foreignKeys = [

        ForeignKey(
            entity = ExercisesSessionEntity::class,
            parentColumns = ["id_session_session", "id_exercise_session"],
            childColumns = ["id_session", "id_exercise"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SeriesEntity(

    @ColumnInfo(name = "id_session") val idSession: Int,
    @ColumnInfo(name = "id_exercise") val idexercise: Int,
    @ColumnInfo(name = "id_serie") val idSerie: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "reps") val reps: Int
)
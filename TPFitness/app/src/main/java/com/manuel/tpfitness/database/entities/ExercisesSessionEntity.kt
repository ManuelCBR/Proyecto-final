package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity (
    tableName = "ejercicios_entrenamiento",
    primaryKeys = ["id_session", "id_exercise"],
    indices = [
        Index(value = ["id_session"], unique = true),
        Index(value = ["id_exercise"], unique = true)

    ],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id_exercise"],
            childColumns = ["id_exercise"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id_session"],
            childColumns = ["id_session"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExercisesSessionEntity (
    @ColumnInfo(name = "id_session") val idSession: Int,
    @ColumnInfo(name = "id_exercise") val idExercise: String
)
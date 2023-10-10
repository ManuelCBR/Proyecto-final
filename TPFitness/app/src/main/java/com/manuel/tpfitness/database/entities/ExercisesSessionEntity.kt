package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity (
    tableName = "ejercicios_entrenamiento",
    primaryKeys = ["id_session_session", "id_exercise_session"],
    /*indices = [
        Index(value = ["id_session_session"]),
        Index(value = ["id_exercise_session"])
    ],*/

    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id_session"],
            childColumns = ["id_session_session"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id_exercise"],
            childColumns = ["id_exercise_session"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        )

    ]
)
data class ExercisesSessionEntity (
    @ColumnInfo(name = "id_session_session") val idSession: Int,
    @ColumnInfo(name = "id_exercise_session") val idExercise: Int
)
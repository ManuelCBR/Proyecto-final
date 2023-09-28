package com.manuel.tpfitness.database.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity (
    tableName = "ejercicios",
    indices = [
        Index(value = ["name_exercise"], unique = true),
        Index(value = ["id_muscle_group_table"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MuscleGroupEntity::class,
            parentColumns = ["id_muscle_group"],
            childColumns = ["id_muscle_group_table"],
            onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE
        )
    ]

)
data class ExerciseEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id_exercise") val idExercise: Int,
    @ColumnInfo (name = "name_exercise") val nameExercise: String,
    @ColumnInfo (name = "description_exercise") val descriptionExercise: String,
    @ColumnInfo (name = "id_muscle_group_table") val idMuscleGroup: Int

)
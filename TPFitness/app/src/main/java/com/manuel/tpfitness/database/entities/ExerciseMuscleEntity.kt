package com.manuel.tpfitness.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseMuscleEntity(
    @Embedded val exercisesTable: ExerciseEntity,
    @Relation(
        parentColumn = "id_muscle_group_table",
        entityColumn = "id_muscle_group"
    )
    val muscleGroupTable: MuscleGroupEntity
)
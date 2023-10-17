package com.manuel.tpfitness.database.entities

import androidx.room.Embedded

data class FullSession (
    @Embedded val session: SessionEntity,
    val idExercise: Int,
    val nameExercise: String,
    val idSerie: Int,
    val weight: Int,
    val reps: Int
)
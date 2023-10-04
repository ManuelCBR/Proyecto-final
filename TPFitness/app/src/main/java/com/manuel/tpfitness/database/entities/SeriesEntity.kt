package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "series"
)
data class SeriesEntity (
    //ESTABLECER PK Y FK COMPUESTAS
    @ColumnInfo(name = "id_session") val idSession: Int,
    @ColumnInfo(name = "id_exercise") val idExercise: Int,
    @ColumnInfo(name = "id_serie") val idSerie: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "reps") val reps: Int

)
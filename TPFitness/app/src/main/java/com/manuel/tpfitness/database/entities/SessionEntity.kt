package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entrenamiento")
data class SessionEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_session") val idSession: Int,
    @ColumnInfo(name = "name_session") val nameSession: String,
    @ColumnInfo(name = "date") val dateSession: String,
)
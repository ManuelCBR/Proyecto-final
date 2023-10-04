package com.manuel.tpfitness.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "entrenamientos",
    indices = [
        Index(value = ["id_session"], unique = true)
    ]
)
data class SessionEntity (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id_session") val idSession: Int,
    @ColumnInfo(name = "nameSession") val nameSession: Int,
    @ColumnInfo(name = "dateSession") val dateSession: String
)
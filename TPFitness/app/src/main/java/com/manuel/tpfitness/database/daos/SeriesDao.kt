package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.manuel.tpfitness.database.entities.SeriesEntity

@Dao
interface SeriesDao {


    @Query("SELECT * FROM series")
    suspend fun getSeries(): MutableList<SeriesEntity>


    @Insert
    suspend fun addSerie(series: SeriesEntity)

    @Transaction
    @Update
    suspend fun updateSerie(series: SeriesEntity)

    @Transaction
    @Query("DELETE FROM series WHERE id_serie=:id")
    suspend fun delSerie(id: Int)
}
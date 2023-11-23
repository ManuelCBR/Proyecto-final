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
    @Query("SELECT * FROM series WHERE id_session=:id")
    suspend fun getSerieById(id: Int): MutableList<SeriesEntity>
    @Query("SELECT * FROM series WHERE id_session=:idSession AND id_exercise=:idExercise")
    suspend fun getSerieByIdSessionExercise(idSession: Int, idExercise: Int): MutableList<SeriesEntity>

    @Query("SELECT id_serie FROM series WHERE id_session=:idSession AND id_exercise=:idExercise")
    suspend fun getSerieBySessionExercise(idSession: Int, idExercise: Int): MutableList<Int>
    @Query("SELECT weight FROM series WHERE id_session=:idSession AND id_exercise=:idExercise AND id_serie=:idSerie")
    suspend fun getWeightById(idSession: Int, idExercise: Int, idSerie: Int): Int
    @Query("SELECT reps FROM series WHERE id_session=:idSession AND id_exercise=:idExercise AND id_serie=:idSerie")
    suspend fun getRepsById(idSession: Int, idExercise: Int, idSerie: Int): Int
    @Query("SELECT COUNT (id_serie) FROM series WHERE id_session=:idSession AND id_exercise=:idExercise")
    suspend fun getNumSeries(idSession: Int, idExercise: Int): Int
    @Insert
    suspend fun addSerie(series: SeriesEntity)

    @Transaction
    @Update
    suspend fun updateSerie(series: SeriesEntity)
    @Transaction
    @Query("DELETE FROM series WHERE id_session=:idSession AND id_exercise=:idExercise")
    suspend fun delSerieBySessionExercise(idSession: Int, idExercise:Int)

    @Transaction
    @Query("DELETE FROM series WHERE id_session=:idSession AND id_exercise=:idExercise AND id_serie=:idSerie")
    suspend fun delSerie(idSession: Int, idExercise: Int, idSerie: Int)
}
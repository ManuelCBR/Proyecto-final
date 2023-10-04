package com.manuel.tpfitness.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.manuel.tpfitness.database.entities.SessionEntity

@Dao
interface SessionDao {
    @Query("SELECT * FROM entrenamientos")
    suspend fun getSession(): MutableList<SessionEntity>
    @Insert
    suspend fun addSession(session: SessionEntity)
    @Update
    suspend fun updateSession(session: SessionEntity)
    @Query("DELETE FROM entrenamientos WHERE id_session=:id")
    suspend fun delSession(id: Int)
}
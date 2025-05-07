package com.zayyan0072.usiadiplanetlain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zayyan0072.usiadiplanetlain.model.Mission
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionDao {

    @Insert
    suspend fun insert(mission: Mission)

    @Update
    suspend fun update(mission: Mission)

    @Query("SELECT * FROM mission ORDER BY tanggalMisi ASC")
    fun getMission(): Flow<List<Mission>>

    @Query("SELECT * FROM mission WHERE id = :id")
    suspend fun getMissionById(id: Long): Mission?
}
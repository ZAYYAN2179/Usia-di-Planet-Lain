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

    @Query("SELECT * FROM mission WHERE isDeleted = 0 ORDER BY tanggalMisi ASC")
    fun getMission(): Flow<List<Mission>>

    @Query("SELECT * FROM mission WHERE isDeleted = 1 ORDER BY tanggalMisi ASC")
    fun getDeletedMissions(): Flow<List<Mission>>

    @Query("SELECT * FROM mission WHERE id = :id")
    suspend fun getMissionById(id: Long): Mission?

    @Query("UPDATE mission SET isDeleted = 1 WHERE id = :id")
    suspend fun softDeleteById(id: Long)

    @Query("UPDATE mission SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("DELETE FROM mission WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM mission WHERE isDeleted = 1")
    suspend fun emptyRecycleBin()
}
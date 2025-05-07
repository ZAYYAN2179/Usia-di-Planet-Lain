package com.zayyan0072.usiadiplanetlain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zayyan0072.usiadiplanetlain.model.Mission

@Database(entities = [Mission::class], version = 1, exportSchema = false)
abstract class MissionDb: RoomDatabase() {

    abstract val dao: MissionDao

    companion object {

        @Volatile
        private var INSTANCE: MissionDb? = null

        fun getInstance(context: Context): MissionDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MissionDb::class.java,
                        "mission.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
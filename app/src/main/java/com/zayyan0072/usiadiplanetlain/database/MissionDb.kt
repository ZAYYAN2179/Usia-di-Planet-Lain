package com.zayyan0072.usiadiplanetlain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zayyan0072.usiadiplanetlain.model.Mission

@Database(entities = [Mission::class], version = 2, exportSchema = false)
abstract class MissionDb : RoomDatabase() {

    abstract val dao: MissionDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE mission ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
            }
        }

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
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
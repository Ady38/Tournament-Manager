package com.ady.tournamentmanager.data.match

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Match::class], version = 1, exportSchema = false)
abstract class MatchDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    companion object {
        @Volatile
        private var Instance: MatchDatabase? = null
        fun getDatabase(context: Context): MatchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MatchDatabase::class.java, "match_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
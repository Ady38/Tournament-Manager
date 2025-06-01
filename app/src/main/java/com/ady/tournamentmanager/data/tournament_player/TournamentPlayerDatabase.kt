package com.ady.tournamentmanager.data.tournament_player

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TournamentPlayer::class], version = 1, exportSchema = false)
abstract class TournamentPlayerDatabase : RoomDatabase() {
    abstract fun tournamentPlayerDao(): TournamentPlayerDao
    companion object {
        @Volatile
        private var Instance: TournamentPlayerDatabase? = null
        fun getDatabase(context: Context): TournamentPlayerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TournamentPlayerDatabase::class.java, "tournament_player_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
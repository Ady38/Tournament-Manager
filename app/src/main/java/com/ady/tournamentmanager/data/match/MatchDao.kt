package com.ady.tournamentmanager.data.match

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(match: Match)

    @Update
    suspend fun update(match: Match)

    @Delete
    suspend fun delete(match: Match)

    @Query("SELECT * from matches WHERE id = :id")
    fun getItem(id: Int): Flow<Match>

    @Query("SELECT * from matches ORDER BY id ASC")
    fun getAllItems(): Flow<List<Match>>
}
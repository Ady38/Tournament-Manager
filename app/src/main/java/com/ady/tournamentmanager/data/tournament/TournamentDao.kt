package com.ady.tournamentmanager.data.tournament
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Objekt datoveho pristupu pre tabulku [Tournament]
 */
@Dao
interface TournamentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tournament: Tournament)

    @Update
    suspend fun update(tournament: Tournament)

    @Delete
    suspend fun delete(tournament: Tournament)

    @Query("SELECT * from tournaments WHERE id = :id")
    fun getItem(id: Int): Flow<Tournament>

    @Query("SELECT * from tournaments ORDER BY id ASC")
    fun getAllItems(): Flow<List<Tournament>>
}
package com.ady.tournamentmanager.data.tournament_player
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentPlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tournamentPlayer: TournamentPlayer)

    @Update
    suspend fun update(tournamentPlayer: TournamentPlayer)

    @Delete
    suspend fun delete(tournamentPlayer: TournamentPlayer)

    @Query("SELECT * from tournament_players WHERE id = :id")
    fun getItem(id: Int): Flow<TournamentPlayer>

    @Query("SELECT * from tournament_players ORDER BY points DESC")
    fun getAllItems(): Flow<List<TournamentPlayer>>
}
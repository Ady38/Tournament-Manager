package com.ady.tournamentmanager.data.tournament_player

import kotlinx.coroutines.flow.Flow

/**
 * Repozitar ktory zabezpecuje pracu s tabulkou [TournamentPlayer] v databaze
 */
interface TournamentPlayerRepository {

    fun getAllItemsStream(): Flow<List<TournamentPlayer>>

    fun getItemStream(id: Int): Flow<TournamentPlayer?>

    suspend fun insertItem(tournamentPlayer: TournamentPlayer)

    suspend fun deleteItem(tournamentPlayer: TournamentPlayer)

    suspend fun updateItem(tournamentPlayer: TournamentPlayer)
}
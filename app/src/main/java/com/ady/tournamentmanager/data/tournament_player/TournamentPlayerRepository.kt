package com.ady.tournamentmanager.data.tournament_player

import kotlinx.coroutines.flow.Flow

interface TournamentPlayerRepository {

    fun getAllItemsStream(): Flow<List<TournamentPlayer>>

    fun getItemStream(id: Int): Flow<TournamentPlayer?>

    suspend fun insertItem(tournamentPlayer: TournamentPlayer)

    suspend fun deleteItem(tournamentPlayer: TournamentPlayer)

    suspend fun updateItem(tournamentPlayer: TournamentPlayer)
}
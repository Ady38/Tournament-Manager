package com.ady.tournamentmanager.data.match

import kotlinx.coroutines.flow.Flow

interface MatchRepository {

    fun getAllItemsStream(): Flow<List<Match>>

    fun getItemStream(id: Int): Flow<Match?>

    suspend fun insertItem(match: Match)

    suspend fun deleteItem(match: Match)

    suspend fun updateItem(match: Match)
}
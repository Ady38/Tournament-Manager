package com.ady.tournamentmanager.data.tournament

import kotlinx.coroutines.flow.Flow

class OfflineTournamentRepository(private val tournamentDao: TournamentDao) : TournamentRepository {

    override fun getAllItemsStream(): Flow<List<Tournament>> = tournamentDao.getAllItems()
    override fun getItemStream(id: Int): Flow<Tournament?> = tournamentDao.getItem(id)
    override suspend fun insertItem(tournament: Tournament) = tournamentDao.insert(tournament)
    override suspend fun deleteItem(tournament: Tournament) = tournamentDao.delete(tournament)
    override suspend fun updateItem(tournament: Tournament) = tournamentDao.update(tournament)
}

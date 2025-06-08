package com.ady.tournamentmanager.data.tournament_player

import kotlinx.coroutines.flow.Flow

/**
 * Offline implementacia triedy [TournamentPlayerRepository]
 *
 * @property tournamentPlayerDao Objekt datoveho pristupu pre tabulku [TournamentPlayer]
 */
class OfflineTournamentPlayerRepository(private val tournamentPlayerDao: TournamentPlayerDao) : TournamentPlayerRepository {

    override fun getAllItemsStream(): Flow<List<TournamentPlayer>> = tournamentPlayerDao.getAllItems()
    override fun getItemStream(id: Int): Flow<TournamentPlayer?> = tournamentPlayerDao.getItem(id)
    override suspend fun insertItem(player: TournamentPlayer) = tournamentPlayerDao.insert(player)
    override suspend fun deleteItem(player: TournamentPlayer) = tournamentPlayerDao.delete(player)
    override suspend fun updateItem(player: TournamentPlayer) = tournamentPlayerDao.update(player)
}
package com.ady.tournamentmanager.data.match

import kotlinx.coroutines.flow.Flow

/**
 * Offline implementacia triedy [MatchRepository]
 *
 * @property matchDao Objekt datoveho pristupu pre tabulku [Match]
 */
class OfflineMatchRepository(private val matchDao: MatchDao) : MatchRepository {

    override fun getAllItemsStream(): Flow<List<Match>> = matchDao.getAllItems()
    override fun getItemStream(id: Int): Flow<Match?> = matchDao.getItem(id)
    override suspend fun insertItem(match: Match) = matchDao.insert(match)
    override suspend fun deleteItem(match: Match) = matchDao.delete(match)
    override suspend fun updateItem(match: Match) = matchDao.update(match)
}
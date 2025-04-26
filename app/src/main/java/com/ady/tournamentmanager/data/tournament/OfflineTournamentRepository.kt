/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ady.tournamentmanager.data.tournament

import kotlinx.coroutines.flow.Flow

class OfflineTournamentRepository(private val tournamentDao: TournamentDao) : TournamentRepository {

    override fun getAllItemsStream(): Flow<List<Tournament>> = tournamentDao.getAllItems()
    override fun getItemStream(id: Int): Flow<Tournament?> = tournamentDao.getItem(id)
    override suspend fun insertItem(tournament: Tournament) = tournamentDao.insert(tournament)
    override suspend fun deleteItem(tournament: Tournament) = tournamentDao.delete(tournament)
    override suspend fun updateItem(tournament: Tournament) = tournamentDao.update(tournament)
}

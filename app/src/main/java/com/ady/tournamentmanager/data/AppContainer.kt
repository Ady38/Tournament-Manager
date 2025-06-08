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

package com.ady.tournamentmanager.data

import android.content.Context
import com.ady.tournamentmanager.data.match.MatchDatabase
import com.ady.tournamentmanager.data.match.MatchRepository
import com.ady.tournamentmanager.data.match.OfflineMatchRepository
import com.ady.tournamentmanager.data.tournament.OfflineTournamentRepository
import com.ady.tournamentmanager.data.tournament.TournamentDatabase
import com.ady.tournamentmanager.data.tournament.TournamentRepository
import com.ady.tournamentmanager.data.tournament_player.OfflineTournamentPlayerRepository
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayerDatabase
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayerRepository


/**
 * Interface pre implementaciu repozitarov pre jednotlive tabulky v databaze
 */
interface AppContainer {
    val tournamentRepository: TournamentRepository
    val tournamentPlayerRepository: TournamentPlayerRepository
    val matchRepository: MatchRepository
}

/**
 * Implementacia [AppContainer] ktora inicializuje repozitare pre jednotlive tabulky
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val tournamentRepository: TournamentRepository by lazy {
        OfflineTournamentRepository(TournamentDatabase.getDatabase(context).tournamentDao())
    }
    override val tournamentPlayerRepository: TournamentPlayerRepository by lazy {
        OfflineTournamentPlayerRepository(TournamentPlayerDatabase.getDatabase(context).tournamentPlayerDao())
    }
    override val matchRepository: MatchRepository by lazy {
        OfflineMatchRepository(MatchDatabase.getDatabase(context).matchDao())
    }
}

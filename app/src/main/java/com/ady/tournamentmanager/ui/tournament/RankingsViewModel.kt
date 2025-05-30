package com.ady.tournamentmanager.ui.tournament

import androidx.lifecycle.ViewModel
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentRepository
import kotlinx.coroutines.flow.Flow

class RankingsViewModel(private val tournamentRepository: TournamentRepository) : ViewModel() {
    fun getDatabase(): Flow<List<Tournament>> {
        return tournamentRepository.getAllItemsStream()
    }
}
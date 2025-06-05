package com.ady.tournamentmanager.ui.pairings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentRepository
import kotlinx.coroutines.launch

class PairingsViewModel(private val tournamentRepository: TournamentRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateTournament(tournament: Tournament) {
        viewModelScope.launch {
            tournamentRepository.updateItem(tournament)
        }
    }





}
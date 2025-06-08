package com.ady.tournamentmanager.ui.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class LoadTournamentViewModel(private val tournamentRepository: TournamentRepository) : ViewModel() {

    var selectedTournament: Tournament = Tournament(name = "", firstStage = "", secondStage = "")

    fun selectTournament(i: Int) {
        viewModelScope.launch {
            val tournament = tournamentRepository.getItemStream(i).firstOrNull()
            tournament?.let {
                selectedTournament = it
            }
        }
    }

    fun deleteTournament(tournament: Tournament) {
        viewModelScope.launch {
            tournamentRepository.deleteItem(tournament)
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    val tournamentUiState: StateFlow<TournamentUiState> =
        tournamentRepository.getAllItemsStream().map { TournamentUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TournamentUiState()
            )

}

data class TournamentUiState(val itemList: List<Tournament> = listOf())
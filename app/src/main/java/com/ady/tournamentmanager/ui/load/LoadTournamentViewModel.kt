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

/**
 * ViewModel pre nacitanie turnaja
 *
 * Viewmodel udrziava data a zabezpecuje logiku pre nacitanie turnaja
 *
 * @property tournamentRepository repositar ktory je pouzity na spravu turnajov v databaze
 */
class LoadTournamentViewModel(private val tournamentRepository: TournamentRepository) : ViewModel() {

    var selectedTournament: Tournament = Tournament(name = "", firstStage = "", secondStage = "")

    /**
     * Funkcia ktora nastavi vybrany turnaj
     */
    fun selectTournament(i: Int) {
        viewModelScope.launch {
            val tournament = tournamentRepository.getItemStream(i).firstOrNull()
            tournament?.let {
                selectedTournament = it
            }
        }
    }

    /**
     * Funkcia ktora vymaze vybrany turnaj
     */
    fun deleteTournament(tournament: Tournament) {
        viewModelScope.launch {
            tournamentRepository.deleteItem(tournament)
        }
    }


    private companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /**
     * Funkcia ktora vrati zoznam vsetkych turnajov
     */
    val tournamentUiState: StateFlow<TournamentUiState> =
        tournamentRepository.getAllItemsStream().map { TournamentUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TournamentUiState()
            )

}

/**
 * Trieda ktora udrzi data pre zobrazenie
 */
data class TournamentUiState(val itemList: List<Tournament> = listOf())
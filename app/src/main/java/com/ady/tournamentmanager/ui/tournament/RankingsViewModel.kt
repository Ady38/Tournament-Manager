package com.ady.tournamentmanager.ui.tournament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Trieda pre zobrazenie zoznamu hracov
 * @param tournamentPlayerRepository Repozitar hracov
 */
class RankingsViewModel(private val tournamentPlayerRepository: TournamentPlayerRepository) : ViewModel() {

    var tournament = Tournament(name = "", firstStage = "", secondStage = "")
    var playerCount = 0

    private companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /**
     * Funkcia ktora zmaze hraca
     */
    fun deletePlayer(player: TournamentPlayer) {
        viewModelScope.launch {
            tournamentPlayerRepository.deleteItem(player)
            playerCount--
        }
    }

    /**
     * Objekt ktory obsahuje hracov daneho turnaja
     */
    val tournamentPlayerUiState: StateFlow<TournamentPlayerUiState> =
        tournamentPlayerRepository.getAllItemsStream().map { TournamentPlayerUiState(it.filter { player -> player.tournament == tournament.id }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TournamentPlayerUiState()
            )


}

/**
 * Objekt pre stav hracov
 */
data class TournamentPlayerUiState(val itemList: List<TournamentPlayer> = listOf())
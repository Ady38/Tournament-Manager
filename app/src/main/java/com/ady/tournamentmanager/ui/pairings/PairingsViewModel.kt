package com.ady.tournamentmanager.ui.pairings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ady.tournamentmanager.data.match.Match
import com.ady.tournamentmanager.data.match.MatchRepository
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentRepository
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PairingsViewModel(
    private val tournamentRepository: TournamentRepository,
    private val tournamentPlayerRepository: TournamentPlayerRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    var tournament = Tournament(name = "", firstStage = "", secondStage = "")

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateTournament(tournament: Tournament) {
        viewModelScope.launch {
            tournamentRepository.updateItem(tournament)
        }
    }

    val matchUiState: StateFlow<MatchUiState> =
        matchRepository.getAllItemsStream().map {
            MatchUiState(it.filter { match -> match.tournament == tournament.id && match.round == tournament.round })
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MatchUiState()
            )

    suspend fun getPlayer1FromMatch(match: Match): TournamentPlayer? {
        return tournamentPlayerRepository.getItemStream(match.player1).map {
            it
        }.stateIn(scope = viewModelScope).value
    }

    suspend fun getPlayer2FromMatch(match: Match): TournamentPlayer? {
        return tournamentPlayerRepository.getItemStream(match.player2).map {
            it
        }.stateIn(scope = viewModelScope).value
    }


}

data class MatchUiState(val itemList: List<Match> = listOf())
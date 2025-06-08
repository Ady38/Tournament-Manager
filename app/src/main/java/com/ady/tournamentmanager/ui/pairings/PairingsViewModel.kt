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
import kotlinx.coroutines.flow.first
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

    fun updateMatch(match: Match) {
        viewModelScope.launch {
            matchRepository.updateItem(match)
            updatePoints()
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

    suspend fun generatePairings(itemList: List<TournamentPlayer>) {
        PairingsGenerator().generatePairings(tournament, itemList, matchRepository)
    }

    val tournamentPlayerUiState: StateFlow<TournamentPlayerUiState> =
        tournamentPlayerRepository.getAllItemsStream().map { TournamentPlayerUiState(it.filter { player -> player.tournament == tournament.id }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TournamentPlayerUiState()
            )

    suspend fun updatePoints() {
        for (player in tournamentPlayerUiState.value.itemList) {
            var points = 0.0
            val matches = matchRepository.getAllItemsStream().first()
            points += matches.filter{ it.player1 == player.id && it.tournament == tournament.id }.sumOf { it.score1 }
            points += matches.filter{ it.player2 == player.id && it.tournament == tournament.id }.sumOf { it.score2 }
            tournamentPlayerRepository.updateItem(player.copy(points = points))
        }
    }
}

data class MatchUiState(val itemList: List<Match> = listOf())
data class TournamentPlayerUiState(val itemList: List<TournamentPlayer> = listOf())
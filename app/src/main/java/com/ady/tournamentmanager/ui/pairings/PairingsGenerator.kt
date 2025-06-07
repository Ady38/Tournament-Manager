package com.ady.tournamentmanager.ui.pairings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.data.match.Match
import com.ady.tournamentmanager.data.match.MatchRepository
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer

class PairingsGenerator(

) {
    suspend fun generatePairings(
        tournament: Tournament,
        playerList: List<TournamentPlayer>,
        matchRepository: MatchRepository
    ) {
        if (tournament.firstStage == "Single Elminiation"  && tournament.round != 0) {
            playerList.filter({ player ->
                player.points == tournament.round.toDouble() - 1
            })
            val shuffledPlayers = playerList.shuffled()
            for (i in 0 until shuffledPlayers.size step 2) {
                val player1 = shuffledPlayers[i]
                val player2 = if (i + 1 < shuffledPlayers.size) shuffledPlayers[i + 1] else null

                matchRepository.insertItem(Match(player1 = player1.id, player2 = player2?.id ?: 0, tournament = tournament.id, round = tournament.round))

                println("Match: ${player1.name} vs ${player2?.name ?: "BYE"}")
            }
        }
    }
}
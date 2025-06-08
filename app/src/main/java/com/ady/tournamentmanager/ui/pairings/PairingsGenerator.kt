package com.ady.tournamentmanager.ui.pairings

import com.ady.tournamentmanager.data.match.Match
import com.ady.tournamentmanager.data.match.MatchRepository
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer

/**
 * Funkcia generujuca parovanie pre dalsie kolo
 */
suspend fun generatePairings(
    tournament: Tournament,
    playerList: List<TournamentPlayer>,
    matchRepository: MatchRepository
) {
    var players = playerList
    if (tournament.firstStage == "Single Elminiation"  && tournament.round != 0) {
        players = playerList.filter({ player ->
            player.points == tournament.round.toDouble() - 1
        })
        for(player in playerList) {
            print(player.points.toString() + " " + (tournament.round.toDouble() - 1).toString())
            println(player.points == tournament.round.toDouble() - 1)
        }
        players = players.shuffled()
        if (players.size != 1) {
            for (i in 0 until players.size step 2) {
                val player1 = players[i]
                val player2 = if (i + 1 < players.size) players[i + 1] else null

                matchRepository.insertItem(Match(player1 = player1.id, player2 = player2?.id ?: 0, tournament = tournament.id, round = tournament.round))

                println("Match: ${player1.name} vs ${player2?.name ?: "BYE"}")
            }
        }
    }
}
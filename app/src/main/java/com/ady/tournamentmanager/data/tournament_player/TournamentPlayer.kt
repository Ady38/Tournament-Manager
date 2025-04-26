package com.ady.tournamentmanager.data.tournament_player

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ady.tournamentmanager.data.player.Player
import com.ady.tournamentmanager.data.tournament.Tournament

@Entity(tableName = "tournament_players")
data class TournamentPlayer (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tournament: Tournament,
    val player: Player,
    val points: Double
)
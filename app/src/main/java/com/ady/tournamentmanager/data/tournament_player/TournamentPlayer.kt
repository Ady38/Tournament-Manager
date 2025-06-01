package com.ady.tournamentmanager.data.tournament_player

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournament_players")
data class TournamentPlayer (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tournament: Int,
    val name: String,
    val surname: String,
    val points: Double
)
package com.ady.tournamentmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournament players")
data class TournamentPlayers (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tournament: Tournament,
    val player: Player,
    val points: Double
)
package com.ady.tournamentmanager.data.match

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tournament: Int,
    var round: Int,
    val player1: Int,
    val player2: Int,
    val score1: Int,
    val score2: Int,
)
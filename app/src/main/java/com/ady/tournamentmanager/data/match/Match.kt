package com.ady.tournamentmanager.data.match

import androidx.room.Entity
import androidx.room.PrimaryKey

class Match {

    @Entity (tableName = "matches")
    data class Tournament(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val tournament: Int,
        var round: Int,
        val player1: Int,
        val player2: Int,
        val score1: Int,
        val score2: Int,
    )
}
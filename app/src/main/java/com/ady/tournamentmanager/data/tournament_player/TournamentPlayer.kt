package com.ady.tournamentmanager.data.tournament_player

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Trieda reprezentujuca hraca v turnaji
 *
 * @property id Unikatny kluc hraca
 * @property tournament ID Turnaja ktoreho sa hrac zucastnuje.
 * @property name Meno hraca
 * @property surname Priezvisko hraca
 * @property points Pocet bodov ktore hraca ziskal v turnaji
 */
@Entity(tableName = "tournament_players")
data class TournamentPlayer (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tournament: Int,
    val name: String,
    val surname: String,
    val points: Double
)
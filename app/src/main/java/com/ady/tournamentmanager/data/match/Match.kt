package com.ady.tournamentmanager.data.match

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Trieda reprezentujuca zapas
 *
 * @property id Unikatny kluc zapasu
 * @property tournament ID turnaja v ktorom sa zapas odohrava
 * @property round Cislo kola v ktorom sa zapas odohrava
 * @property player1 ID prveho hraca v zapase
 * @property player2 ID druheho hraca v zapase
 * @property score1 Vysledok prveho hraca
 * @property score2 Vysledok druheho hraca
 */
@Entity (tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tournament: Int,
    var round: Int,
    val player1: Int,
    val player2: Int,
    val score1: Double = 0.0,
    val score2: Double = 0.0,
)
package com.ady.tournamentmanager.data.tournament
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Trieda reprezentujuca turnaj
 *
 * @property id Unikatny kluc turnaja
 * @property name Meno turnaja
 * @property firstStage Prva faza turnaja
 * @property secondStage Druha faza turnaja
 * @property round Aktualne kolo turnaja
 */
@Entity (tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val firstStage: String,
    val secondStage: String,
    var round: Int = 0,
)
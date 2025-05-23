package com.ady.tournamentmanager.data.tournament
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity (tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val firstStage: String,
    val secondStage: String,
)
package com.ady.tournamentmanager.ui.add_player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayerRepository

class AddPlayerViewModel(private val tournamentPlayerRepository: TournamentPlayerRepository) : ViewModel() {

    var name = ""
    var surname = ""
    var tournament = Tournament(0, "", "", "")
    var isValid = false

    fun updateName(newName : String) {
        name = newName
        checkValidity()
    }

    fun updateSurname(newSurname: String){
        surname = newSurname
        checkValidity()
    }

    private fun checkValidity() {
        isValid =  (name != "" && surname != "")
    }

    suspend fun saveItem() {
        if (isValid) {
            tournamentPlayerRepository.insertItem(toPlayer())
        }
    }

    fun toPlayer(): TournamentPlayer = TournamentPlayer(
        id = 0,
        name = name,
        surname = surname,
        points = 0.0,
        tournament = tournament.id
    )

}
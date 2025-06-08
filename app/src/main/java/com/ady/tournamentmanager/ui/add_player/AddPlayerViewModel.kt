package com.ady.tournamentmanager.ui.add_player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayerRepository

/**
 * ViewModel pre pridavanie hraca do turnaja
 *
 * Viewmodel udrziava data a zabezpecuje logiku pre pridavanie hraca do turnaja
 *
 * @property tournamentPlayerRepository repositar ktory je pouzity na spravu hracov v databaze
 */
class AddPlayerViewModel(private val tournamentPlayerRepository: TournamentPlayerRepository) : ViewModel() {

    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var tournament by mutableStateOf(Tournament(0, "", "", ""))
    var isValid by mutableStateOf(false)

    /**
     * Funkcia ktora aktualizuje meno hraca
     */
    fun updateName(newName : String) {
        name = newName
        checkValidity()
    }

    /**
     * Funkcia ktora aktualizuje priezvisko hraca
     */
    fun updateSurname(newSurname: String){
        surname = newSurname
        checkValidity()
    }

    private fun checkValidity() {
        isValid =  (name != "" && surname != "")
    }

    /**
     * Funkcia ktora ulozi hraca do databazy
     */
    suspend fun saveItem() {
        if (isValid) {
            tournamentPlayerRepository.insertItem(toPlayer())
        }
    }

    private fun toPlayer(): TournamentPlayer = TournamentPlayer(
        id = 0,
        name = name,
        surname = surname,
        points = 0.0,
        tournament = tournament.id
    )

}
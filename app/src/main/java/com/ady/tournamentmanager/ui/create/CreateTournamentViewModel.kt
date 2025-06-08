package com.ady.tournamentmanager.ui.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentRepository

/**
 * ViewModel pre vytvorenie turnaja
 *
 * Viewmodel udrziava data a zabezpecuje logiku pre vytvorenie turnaja
 *
 * @property tournamentRepository repositar ktory je pouzity na spravu turnajov v databaze
 */
class CreateTournamentViewModel(private val tournamentRepository: TournamentRepository) : ViewModel() {

    var name by mutableStateOf("")
    var phases by mutableStateOf("")
    var firstPhase by mutableStateOf("")
    var secondPhase by mutableStateOf("")
    var isValid by mutableStateOf(false)

    /**
     * Funkcia ktora aktualizuje nazov turnaja
     */
    fun updateName(newName : String) {
        name = newName
        checkValidity()
    }

    /**
     * Funkcia ktora aktualizuje pocet faz turnaja
     */
    fun updatePhasesNumber(newNumber: String) {
        phases = newNumber
        checkValidity()
    }

    /**
     * Funkcia ktora aktualizuje prvu fazu turnaja
     */
    fun updateFirstPhase(newPhase : String) {
        firstPhase = newPhase
        checkValidity()
    }

    /**
     * Funkcia ktora aktualizuje druhu fazu turnaja
     */
    fun updateSecondPhase(newPhase: String) {
        secondPhase = newPhase
        checkValidity()
    }

    private fun checkValidity() {
        isValid =  (name != "" && phases == "1" && firstPhase != "")
                || (name != "" && phases == "2" && firstPhase != "" && secondPhase != "")
    }

    /**
     * Funkcia ktora ulozi turnaj do databazy
     */
    suspend fun saveItem() {
        if (isValid) {
            tournamentRepository.insertItem(toTournament())
        }
    }

    private fun toTournament(): Tournament = Tournament(
        id = 0,
        name = name,
        firstStage = firstPhase,
        secondStage = secondPhase,
    )

}
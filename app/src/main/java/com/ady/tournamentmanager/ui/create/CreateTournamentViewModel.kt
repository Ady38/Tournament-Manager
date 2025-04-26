package com.ady.tournamentmanager.ui.create

import androidx.compose.material3.DatePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament.TournamentRepository
import java.sql.Date
import java.util.Calendar

class CreateTournamentViewModel(private val tournamentRepository: TournamentRepository) : ViewModel() {

    var name by mutableStateOf("")
    var phases by mutableStateOf("")
    var firstPhase by mutableStateOf("")
    var secondPhase by mutableStateOf("")
    var isValid by mutableStateOf(false)

    fun updateName(newName : String) {
        name = newName
        checkValidity()
    }

    fun updatePhasesNumber(newNumber: String) {
        phases = newNumber
        checkValidity()
    }

    fun updateFirstPhase(newPhase : String) {
        firstPhase = newPhase
        checkValidity()
    }

    fun updateSecondPhase(newPhase: String) {
        secondPhase = newPhase
        checkValidity()
    }

    private fun checkValidity() {
        isValid =  (name != "" && phases == "1" && firstPhase != "")
                || (name != "" && phases == "2" && firstPhase != "" && secondPhase != "")
    }

    suspend fun saveItem() {
        if (isValid) {
            tournamentRepository.insertItem(toTournament())
        }
    }

    fun toTournament(): Tournament = Tournament(
        id = 0,
        name = name,
        firstStage = firstPhase,
        secondStage = secondPhase,
        date = Calendar.getInstance().time as Date
    )

}
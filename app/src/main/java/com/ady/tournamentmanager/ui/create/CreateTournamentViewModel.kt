package com.ady.tournamentmanager.ui.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateTournamentViewModel : ViewModel() {
    var name by mutableStateOf("")
    var phases by mutableStateOf("")
    var firstPhase by mutableStateOf("")
    var secondPhase by mutableStateOf("")

    fun UpdateName(newName : String) {
        name = newName
    }

    fun UpdatePhasesNumber(newNumber: String) {
        phases = newNumber
    }

    fun UpdateFirstPhase(newPhase : String) {
        firstPhase = newPhase
    }

    fun UpdateSecondPhase(newPhase: String) {
        secondPhase = newPhase
    }

}
package com.ady.tournamentmanager.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ady.tournamentmanager.TournamentManagerApplication
import com.ady.tournamentmanager.ui.create.CreateTournamentViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            CreateTournamentViewModel(tournamentManagerApplication().container.tournamentRepository)
        }
    }
}

fun CreationExtras.tournamentManagerApplication(): TournamentManagerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TournamentManagerApplication)
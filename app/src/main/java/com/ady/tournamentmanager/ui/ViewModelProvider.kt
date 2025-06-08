package com.ady.tournamentmanager.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ady.tournamentmanager.TournamentManagerApplication
import com.ady.tournamentmanager.ui.add_player.AddPlayerViewModel
import com.ady.tournamentmanager.ui.create.CreateTournamentViewModel
import com.ady.tournamentmanager.ui.load.LoadTournamentViewModel
import com.ady.tournamentmanager.ui.pairings.PairingsViewModel
import com.ady.tournamentmanager.ui.tournament.RankingsViewModel

/**
 * Objekt pre vytvorenie viewmodelov
 */
object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CreateTournamentViewModel(tournamentManagerApplication().container.tournamentRepository)
        }
        initializer {
            RankingsViewModel(tournamentManagerApplication().container.tournamentPlayerRepository)
        }
        initializer {
            LoadTournamentViewModel(tournamentManagerApplication().container.tournamentRepository)
        }
        initializer {
            AddPlayerViewModel(tournamentManagerApplication().container.tournamentPlayerRepository)
        }
        initializer {
            PairingsViewModel(tournamentManagerApplication().container.tournamentRepository, tournamentManagerApplication().container.tournamentPlayerRepository, tournamentManagerApplication().container.matchRepository)
        }
    }
}

/**
 * Funkcia ktora nastavi aplikaciu na aktualny proces aplikacie
 */
fun CreationExtras.tournamentManagerApplication(): TournamentManagerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TournamentManagerApplication)
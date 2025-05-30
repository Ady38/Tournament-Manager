package com.ady.tournamentmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ady.tournamentmanager.ui.start.StartDestination
import com.ady.tournamentmanager.ui.start.StartScreen
import androidx.navigation.compose.composable
import com.ady.tournamentmanager.ui.ViewModelProvider
import com.ady.tournamentmanager.ui.create.CreateTournamentDestination
import com.ady.tournamentmanager.ui.create.CreateTournamentScreen
import com.ady.tournamentmanager.ui.load.LoadTournamentDestination
import com.ady.tournamentmanager.ui.load.LoadTournamentViewModel
import com.ady.tournamentmanager.ui.load.LoadTournamentScreen
import com.ady.tournamentmanager.ui.tournament.RankingsDestination
import com.ady.tournamentmanager.ui.tournament.RankingsScreen

@Composable
fun TournamentManagerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loadTournamentViewModel: LoadTournamentViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = StartDestination.route,
        modifier = modifier
    ) {
        composable(route = StartDestination.route) {
            StartScreen(
                modifier = modifier,
                navigateToCreate = { navController.navigate(CreateTournamentDestination.route) },
                navigateToLoad = { navController.navigate(LoadTournamentDestination.route) },
            )
        }
        composable (route = LoadTournamentDestination.route) {
            LoadTournamentScreen(
                onNavigateUp = { navController.navigate(StartDestination.route) },
                navigateToRankings = { navController.navigate(RankingsDestination.route) },
                viewModel = loadTournamentViewModel
            )
        }
        composable (route = CreateTournamentDestination.route) {
            CreateTournamentScreen(
                onNavigateUp = { navController.navigate(StartDestination.route) }
            )
        }
        composable (route = RankingsDestination.route) {
            RankingsScreen(
                onNavigateUp = { navController.navigate(StartDestination.route)},
                tournament = loadTournamentViewModel.selectedTournament
            )
        }
    }
}
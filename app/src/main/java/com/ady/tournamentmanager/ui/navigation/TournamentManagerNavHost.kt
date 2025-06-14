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
import com.ady.tournamentmanager.ui.add_player.AddPlayerDestination
import com.ady.tournamentmanager.ui.add_player.AddPlayerScreen
import com.ady.tournamentmanager.ui.create.CreateTournamentDestination
import com.ady.tournamentmanager.ui.create.CreateTournamentScreen
import com.ady.tournamentmanager.ui.load.LoadTournamentDestination
import com.ady.tournamentmanager.ui.load.LoadTournamentViewModel
import com.ady.tournamentmanager.ui.load.LoadTournamentScreen
import com.ady.tournamentmanager.ui.pairings.PairingsDestination
import com.ady.tournamentmanager.ui.pairings.PairingsScreen
import com.ady.tournamentmanager.ui.tournament.RankingsDestination
import com.ady.tournamentmanager.ui.tournament.RankingsScreen


/**
 * Funkcia ktora definuje navigacny graf aplikacie
 *
 * @param navController Ovaldac navigacie ktory ovlada aplikaciu.
 * @param modifier [Modifier] ktory bude pouzity, zakladne prazdny
 * @param loadTournamentViewModel Instancia viewmodelu ktory je pouzity na nacitanie turnajov
 */
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
                navigateToPlayerAdd = { navController.navigate(AddPlayerDestination.route) },
                navigateToPairings = { navController.navigate(PairingsDestination.route) },
                tournament = loadTournamentViewModel.selectedTournament
            )
        }
        composable (route = AddPlayerDestination.route) {
            AddPlayerScreen(
                onNavigateUp = { navController.navigate(RankingsDestination.route) },
                tournament = loadTournamentViewModel.selectedTournament
            )
        }
        composable(route = PairingsDestination.route) {
            PairingsScreen(
                onNavigateUp = { navController.navigate(RankingsDestination.route) },
                navigateToNewRound = { navController.navigate(PairingsDestination.route) },
                tournament = loadTournamentViewModel.selectedTournament
            )
        }
    }
}
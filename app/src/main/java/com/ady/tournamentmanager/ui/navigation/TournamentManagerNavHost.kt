package com.ady.tournamentmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ady.tournamentmanager.ui.StartDestination
import com.ady.tournamentmanager.ui.StartScreen
import androidx.navigation.compose.composable
import com.ady.tournamentmanager.ui.CreateTournamentDestination
import com.ady.tournamentmanager.ui.CreateTournamentScreen

@Composable
fun TournamentManagerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
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
                navigateToLoad = { navController.navigate(StartDestination.route) },
                navigateToPlayers = { navController.navigate(StartDestination.route) }
            )
        }
        composable (route = CreateTournamentDestination.route) {
            CreateTournamentScreen(
                onNavigateUp = { navController.navigate(StartDestination.route) }
            )
        }
    }
}
package com.ady.tournamentmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ady.tournamentmanager.ui.StartDestination
import com.ady.tournamentmanager.ui.StartScreen
import androidx.navigation.compose.composable

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
                navigateToCreate = { navController.navigate(StartDestination.route) },
                navigateToLoad = { navController.navigate(StartDestination.route) },
                navigateToPlayers = { navController.navigate(StartDestination.route) }
            )
        }
    }
}
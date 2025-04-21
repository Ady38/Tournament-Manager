package com.ady.tournamentmanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ady.tournamentmanager.ui.navigation.TournamentManagerNavHost

@Composable
fun TournamentManagerApp(
    navController: NavHostController = rememberNavController()
) {
    TournamentManagerNavHost(
        navController = navController,
        modifier = Modifier
            .fillMaxSize()
    )
}
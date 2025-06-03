package com.ady.tournamentmanager.ui.pairings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.ui.navigation.NavigationDestination

object PairingsDestination : NavigationDestination {
    override val route = "pairings"
    override val titleRes = R.string.pairings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairingsScreen (
    onNavigateUp: () -> Unit,
    tournament: Tournament,
    //viewModel: PairingsViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = {
            TournamentManagerTopAppBar(
                title = tournament.name + " " + stringResource(PairingsDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp
            )
        },
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "works")
        }
    }
}
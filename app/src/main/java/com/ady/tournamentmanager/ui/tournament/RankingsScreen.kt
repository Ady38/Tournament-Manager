package com.ady.tournamentmanager.ui.tournament

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.ui.ViewModelProvider
import com.ady.tournamentmanager.ui.navigation.NavigationDestination

object RankingsDestination : NavigationDestination {
    override val route = "rankings"
    override val titleRes = R.string.rankings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingsScreen (
    onNavigateUp: () -> Unit,
    viewModel: RankingsViewModel = viewModel(factory = ViewModelProvider.Factory),
    tournament: Tournament = Tournament(name = "", firstStage = "", secondStage = "")
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var tournament = tournament
    //if (tournament.name == "") {
    //    val tournaments = viewModel.getDatabase().collectAsState(initial = emptyList())
    //    tournament = tournaments.value.lastOrNull() ?: Tournament(name = "", firstStage = "", secondStage = "")
    //}
    Scaffold (
        topBar = {
            TournamentManagerTopAppBar(
                title = tournament.name + " " + stringResource(RankingsDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }
}
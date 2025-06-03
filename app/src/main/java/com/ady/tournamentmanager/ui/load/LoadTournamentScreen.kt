package com.ady.tournamentmanager.ui.load

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.ui.ViewModelProvider
import com.ady.tournamentmanager.ui.create.CreateTournamentDestination
import com.ady.tournamentmanager.ui.navigation.NavigationDestination

object LoadTournamentDestination : NavigationDestination {
    override val route = "load tournament"
    override val titleRes = R.string.load_tournament
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LoadTournamentScreen (
    onNavigateUp: () -> Unit,
    navigateToRankings : () -> Unit,
    viewModel: LoadTournamentViewModel = viewModel(factory = ViewModelProvider.Factory),

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TournamentManagerTopAppBar(
                title = stringResource(CreateTournamentDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp
            )
        }
    ){ innerPadding ->
        LoadBody(
            tournamentList = viewModel.tournamentUiState.collectAsState().value.itemList,
            onItemClick = {
                viewModel.selectTournament(it)
                navigateToRankings()
            },
            onItemHold = { viewModel.deleteTournament(it)
            },
            contentPadding = innerPadding,
        )
    }
}

@Composable
fun LoadBody(
    tournamentList: List<Tournament>,
    onItemHold: (Tournament) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (tournamentList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_tournament_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            TournamentList(
                tournamentList = tournamentList,
                onItemClick = { onItemClick(it.id) },
                onItemHold = { onItemHold(it) },
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
fun TournamentList(
    tournamentList: List<Tournament>,
    onItemClick: (Tournament) -> Unit,
    onItemHold: (Tournament) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = tournamentList, key = { it.id }) { item ->
            TournamentListItem(
                tournament = item,
                onItemClick = { onItemClick(item) },
                onItemHold = { onItemHold(item) },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TournamentListItem(
    tournament: Tournament,
    onItemClick: (Tournament) -> Unit,
    onItemHold: (Tournament) -> Unit,
    modifier: Modifier = Modifier
) { val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            text = {
                Text(stringResource(R.string.delete_tournament_confirmation) + tournament.name)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onItemHold(tournament)
                        showDialog.value = false
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }
    Card(
        modifier = modifier.combinedClickable (
                onClick = { onItemClick(tournament) },
                onLongClick = { showDialog.value = true }
            ).fillMaxWidth()
    ){
        Column {
            Row {
                Text(text = tournament.name, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            }
            Row {
                Text(text = tournament.firstStage, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            }
            Row {
                Text(text = tournament.secondStage, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            }
        }

    }
}

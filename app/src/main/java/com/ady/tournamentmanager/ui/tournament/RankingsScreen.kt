package com.ady.tournamentmanager.ui.tournament

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer
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
    navigateToPlayerAdd: () -> Unit,
    navigateToPairings: () -> Unit,
    tournament: Tournament,
    viewModel: RankingsViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    viewModel.tournament = tournament
    viewModel.playerCount = viewModel.tournamentPlayerUiState.collectAsState().value.itemList.size
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TournamentManagerTopAppBar(
                title = tournament.name + " " + stringResource(RankingsDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            Column(
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (viewModel.playerCount < 16) {
                            navigateToPlayerAdd()
                        }},
                    shape = MaterialTheme.shapes.medium,
                    containerColor = if (viewModel.playerCount < 16) MaterialTheme.colorScheme.primary else Color.Gray,
                    text = {
                        Text(text = viewModel.playerCount.toString(), color = Color.White)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_player_title)
                        )
                    },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))

                )
                ExtendedFloatingActionButton(
                    onClick = navigateToPairings,
                    shape = MaterialTheme.shapes.medium,
                    text = {
                        Text(viewModel.tournament.round.toString(), color = MaterialTheme.colorScheme.primary)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = stringResource(R.string.generate_pairings)
                        )
                    },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))

                )
            }

        },
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RankingsBody(
                playerList = viewModel.tournamentPlayerUiState.collectAsState().value.itemList,
                onItemHold = { viewModel.deletePlayer(it) },
                contentPadding = innerPadding
            )
        }
    }
}



@Composable
fun RankingsBody(
    playerList: List<TournamentPlayer>,
    onItemHold: (TournamentPlayer) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (playerList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_player_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            TournamentList(
                playerList = playerList,
                onItemHold = { onItemHold(it) },
                contentPadding = contentPadding
            )
        }
    }
}

@Composable
fun TournamentList(
    playerList: List<TournamentPlayer>,
    onItemHold: (TournamentPlayer) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    var i: Int = 0;
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = playerList, key = { it.id }) { item ->
            TournamentPlayerListItem(
                player = item,
                onItemHold = { onItemHold(item) },
                ranking = ++i,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TournamentPlayerListItem(
    player: TournamentPlayer,
    onItemHold: (TournamentPlayer) -> Unit,
    ranking: Int,
    modifier: Modifier = Modifier
) { val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            text = {
                Text(stringResource(R.string.delete_player_confirmation) + player.name + " " + player.surname)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onItemHold(player)
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
        modifier = modifier
            .combinedClickable(
                onClick = {},
                onLongClick = { showDialog.value = true }
            )
            .fillMaxWidth()
    ){
        Column {
            Row {
                Text(text = ranking.toString() + stringResource(R.string.dot) + player.name + stringResource(R.string.space) + player.surname, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                Text(text = player.points.toString(), modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)), textAlign = TextAlign.End)
            }
        }

    }
}
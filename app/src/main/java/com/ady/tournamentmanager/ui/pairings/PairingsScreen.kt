package com.ady.tournamentmanager.ui.pairings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.data.match.Match
import com.ady.tournamentmanager.data.tournament.Tournament
import com.ady.tournamentmanager.data.tournament_player.TournamentPlayer
import com.ady.tournamentmanager.ui.ViewModelProvider
import com.ady.tournamentmanager.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object PairingsDestination : NavigationDestination {
    override val route = "pairings"
    override val titleRes = R.string.pairings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairingsScreen (
    onNavigateUp: () -> Unit,
    navigateToNewRound: () -> Unit,
    tournament: Tournament,
    viewModel: PairingsViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    viewModel.tournament = tournament
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = {
            TournamentManagerTopAppBar(
                title = tournament.name + " " + stringResource(PairingsDestination.titleRes) + " - " + tournament.round.toString(),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            Column(
            ){
                FloatingActionButton(
                    onClick = {
                        tournament.round++
                        viewModel.updateTournament(tournament)
                        coroutineScope.launch {
                            viewModel.generatePairings()
                        }
                        navigateToNewRound()
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.generate_pairings)
                    )
                }
                FloatingActionButton(
                    onClick = {
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(text = stringResource(R.string.timer))
                }
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MatchList(
                matchList = viewModel.matchUiState.collectAsState().value.itemList,
                contentPadding = innerPadding,
                viewModel = viewModel
            )
        }
    }
}



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DropDownMenu(
    list: List<String>,
    onTextChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false)}

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {isExpanded = !isExpanded },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = value,
            onValueChange = { },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false}
        ) {
            list.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {Text (text = text)},
                    onClick = {
                        onTextChange(list[index])
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun MatchList(
    matchList: List<Match>,
    contentPadding: PaddingValues,
    viewModel: PairingsViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = matchList, key = { it.id }) { item ->
            MatchCard(
                item = item,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MatchCard(
    item: Match,
    viewModel: PairingsViewModel,
    modifier: Modifier = Modifier
) {
    var player1 by remember { mutableStateOf<TournamentPlayer?>(null) }
    var player2 by remember { mutableStateOf<TournamentPlayer?>(null) }

    LaunchedEffect(item) {
        player1 = viewModel.getPlayer1FromMatch(item)
        player2 = viewModel.getPlayer2FromMatch(item)
    }


    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = player1?.name ?: "BYE",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            DropDownMenu(
                listOf("1:0", "0.5:0.5", "0:1"),
                onTextChange = {},
                value = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            Text(text = player2?.name ?: "BYE", modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f), textAlign = TextAlign.End)
        }
    }
}
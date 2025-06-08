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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
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
import com.ady.tournamentmanager.utils.DropDownMenu
import kotlinx.coroutines.launch

/**
 * Objekt pre navigaciu do obrazovky parovania
 */
object PairingsDestination : NavigationDestination {
    override val route = "pairings"
    override val titleRes = R.string.pairings
}

/**
 * Funkcia ktora zobrazuje obrazovku parovania
 * @param onNavigateUp Funkcia ktora sa vola pri stlaceni tlacidla spat
 * @param navigateToNewRound Funkcia ktora sa vola pri vytvoreni noveho kola
 * @param tournament Turnaj ktory sa bude parovat
 * @param viewModel Instancia viewmodelu ktory je pouzity na parovanie
 */
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
    val tournamentPlayerUiState by viewModel.tournamentPlayerUiState.collectAsState()
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
            Column{
                FloatingActionButton(
                    onClick = {
                        if (!viewModel.tournament.finished) {
                            tournament.round++
                            viewModel.updateTournament(tournament)
                            coroutineScope.launch {
                                viewModel.generatePairings(tournamentPlayerUiState.itemList)
                            }
                            navigateToNewRound()
                        }
                    },
                    containerColor = if (!viewModel.tournament.finished) MaterialTheme.colorScheme.primary else Color.Gray,
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
                    containerColor = Color.Gray,
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
            val matches = viewModel.matchUiState.collectAsState().value.itemList
            if(matches.isNotEmpty()) {
                MatchList(
                    matchList = matches,
                    contentPadding = innerPadding,
                    viewModel = viewModel
                )
            } else {
                Text(text = stringResource(R.string.finished))
                viewModel.tournament.finished = true
                viewModel.updateTournament(viewModel.tournament)
            }
        }
    }
}

/**
 * Funkcia ktora zobrazuje zoznam zapasov
 */
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

/**
 * Funkcia ktora zobrazuje jednotliveho hraca
 */
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
            var player1Text = stringResource(R.string.bye)
            var player2Text = stringResource(R.string.bye)
            var resultText = ""
            if(player1 != null){
                player1Text = player1!!.name.first() + stringResource(R.string.dot) + player1!!.surname
            }
            if(player2 != null){
                player2Text = player2!!.name.first() + stringResource(R.string.dot) + player2!!.surname
            }
            resultText = item.score1.toString() + ":" + item.score2.toString()
            var list = listOf(stringResource(R.string.Win), stringResource(R.string.draw),
                stringResource(R.string.Lose)
            )
            if(viewModel.tournament.firstStage == "Single Elminiation"){
                list = listOf(stringResource(R.string.Win), stringResource(R.string.Lose))
            }
            Text(
                text = player1Text,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            DropDownMenu(
                list = list,
                onTextChange = {
                    if(it == "1:0"){
                        viewModel.updateMatch(Match(id = item.id, player1 = item.player1, player2 = item.player2, tournament = item.tournament, round = item.round, score1 = 1.0, score2 = 0.0 ))
                    }
                    if(it == "0.5:0.5"){
                        viewModel.updateMatch(Match(id = item.id, player1 = item.player1, player2 = item.player2, tournament = item.tournament, round = item.round, score1 = 0.5, score2 = 0.5 ))
                    }
                    if(it == "0:1") {
                        viewModel.updateMatch(Match(id = item.id, player1 = item.player1, player2 = item.player2, tournament = item.tournament, round = item.round, score1 = 0.0, score2 = 1.0 ))
                    }
                },
                value = resultText,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
            Text(
                text = player2Text,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f), textAlign = TextAlign.End)
        }
    }
}
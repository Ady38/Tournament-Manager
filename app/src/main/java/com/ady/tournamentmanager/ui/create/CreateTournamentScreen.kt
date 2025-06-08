package com.ady.tournamentmanager.ui.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.ui.ViewModelProvider
import com.ady.tournamentmanager.ui.navigation.NavigationDestination
import com.ady.tournamentmanager.ui.theme.TournamentManagerTheme
import com.ady.tournamentmanager.utils.DropDownMenu
import kotlinx.coroutines.launch

/**
 * Objekt obashujuci data pre navigaciu do obrazovky vytvorenia turnaja
 */
object CreateTournamentDestination : NavigationDestination {
    override val route = "create tournament"
    override val titleRes = R.string.create_tournament
}

/**
 * Trieda ktora zabezpecuje zobrazenie obrazovky pre vytvorenie turnaja
 * @param onNavigateUp Funkcia ktora sa vola pri stlaceni spatneho tlacidla
 * @param viewModel ViewModel ktory je pouzity na spravu dat a logiku pre vytvorenie turnaja
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTournamentScreen (
    onNavigateUp: () -> Unit,
    viewModel: CreateTournamentViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

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
        Column(
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
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text(stringResource(R.string.name_input)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            Text(
                text = "Choose number of phases"
            )
            DropDownMenu(
                list = listOf(stringResource(R.string._1)),//, stringResource(R.string._2)),
                onTextChange = { viewModel.updatePhasesNumber(it) },
                value = viewModel.phases,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = stringResource(R.string.choose_first_phase)
            )
            DropDownMenu(
                listOf(stringResource(R.string.single_elminiation)), //stringResource(R.string.round_robin), ),
                onTextChange = { viewModel.updateFirstPhase(it) },
                value = viewModel.firstPhase,
                Modifier
                    .weight(1f)
            )
            if (viewModel.phases == "2"){
                Text(
                    text = stringResource(R.string.choose_second_phase)
                )
                DropDownMenu(
                    listOf(stringResource(R.string.single_elminiation)),
                    onTextChange = { viewModel.updateSecondPhase(it) },
                    value = viewModel.secondPhase,
                    Modifier
                        .weight(1f)
                )
            }   else {
                viewModel.updateSecondPhase("")
            }
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                    }
                    onNavigateUp()
                          },
                modifier = Modifier.widthIn(min = 300.dp),
                enabled = viewModel.isValid
            ) {
                Text(
                    text = stringResource(R.string.create_tournament)
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateTournamentPreview() {
    TournamentManagerTheme {
        CreateTournamentScreen(
            onNavigateUp = {},
        )
    }
}
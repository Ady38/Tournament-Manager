package com.ady.tournamentmanager.ui.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.TournamentManagerTopAppBar
import com.ady.tournamentmanager.ui.navigation.NavigationDestination
import com.ady.tournamentmanager.ui.theme.TournamentManagerTheme

object CreateTournamentDestination : NavigationDestination {
    override val route = "create tournament"
    override val titleRes = R.string.create_tournament
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTournamentScreen (
    onNavigateUp: () -> Unit,
    navigateToRankings : () -> Unit,
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
                value = "",
                onValueChange = { },
                label = { Text(stringResource(R.string.name_input)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            DropDownMenu(
                list = listOf("1","2"),
                label = R.string.phases_input
            )
            DropDownMenu(
                list = listOf("Swiss", "Round Robin", "Single Elminiation"),
                label = R.string.first_phase
            )
            DropDownMenu(
                listOf("Round Robin", "Single Elminiation"),
                label = R.string.second_phase
            )
            OutlinedButton(
                onClick = navigateToRankings,
                modifier = Modifier.widthIn(min = 300.dp)
            ) {
                Text(
                    text = stringResource(R.string.create_tournament)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DropDownMenu(
    list: List<String>,
    label: Int
) {
    var isExpanded by remember { mutableStateOf(false)}
    var selectedText by remember { mutableStateOf(list[0]) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {isExpanded = !isExpanded }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            label = { stringResource(label)}
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false}
        ) {
            list.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {Text (text = text)},
                    onClick = {
                        selectedText = list[index]
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
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
            navigateToRankings = {}
        )
    }
}
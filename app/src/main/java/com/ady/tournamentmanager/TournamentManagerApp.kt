package com.ady.tournamentmanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

/**
 * Funkcia ktora nastavi top bar
 * @param title Title top baru
 * @param canNavigateBack nastavi ci je mozne ist spat
 * @param modifier Modifier pre top bar, zakladne prazdny
 * @param scrollBehavior nastavi scrollBehavior pre top bar
 * @param navigateUp akcia ktora sa vykona pri stlaceni spat
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentManagerTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
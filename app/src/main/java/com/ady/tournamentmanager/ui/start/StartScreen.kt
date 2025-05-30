package com.ady.tournamentmanager.ui.start

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ady.tournamentmanager.R
import com.ady.tournamentmanager.ui.navigation.NavigationDestination
import com.ady.tournamentmanager.ui.theme.TournamentManagerTheme
import kotlin.system.exitProcess

object StartDestination : NavigationDestination {
    override val route = "start"
    override val titleRes = R.string.app_name
}

@SuppressLint("ContextCastToActivity") //Need to cast due to app termination
@Composable
fun StartScreen(
    modifier : Modifier = Modifier,
    navigateToCreate : () -> Unit,
    navigateToLoad: () -> Unit,
) {
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.author)
        )
        Spacer(
            modifier = Modifier.height(100.dp)
        )
        OutlinedButton(
            onClick = navigateToCreate,
            modifier = Modifier.widthIn(min = 300.dp)
        ) {
            Text(
                text = stringResource(R.string.create_tournament)
            )
        }
        OutlinedButton(
            onClick = navigateToLoad,
            modifier = Modifier.widthIn(min = 300.dp)
        ) {
            Text(
                text = stringResource(R.string.load_tournament)
            )
        }
        OutlinedButton(
            onClick = { exitApp(activity)
            },
            modifier = Modifier.widthIn(min = 300.dp)
        ) {
            Text(
                text = stringResource(R.string.quit)
            )
        }
    }
}

fun exitApp(activity: Activity?) {
    activity?.finishAndRemoveTask()
    exitProcess(0)
}

@Preview
@Composable
fun StartPreview() {
    TournamentManagerTheme {
        StartScreen(
            navigateToCreate = {},
            navigateToLoad = {},
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
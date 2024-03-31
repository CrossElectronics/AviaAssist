package crosstech.aviaassist.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import crosstech.aviaassist.R
import crosstech.aviaassist.ui.theme.AviaAssistTheme

@Composable
fun AviaAssistScreen(
    flightViewModel: FlightViewModel = viewModel()
) {
    val clipboardManager = LocalClipboardManager.current
    val uiState by flightViewModel.uiState.collectAsState()
    flightViewModel.initFlightDataAndAirportMap(LocalContext.current)
    Scaffold(
        modifier = Modifier,
        topBar = { AviaAssistTopBar() },
        bottomBar = {},
        floatingActionButton = {
            AviaAssistFab {
                val text = clipboardManager.getText()?.text
                if (text != null) {
                    flightViewModel.uploadMissionsGroupedByDate(text)
                }
            }
        }
    ) {
        // TODO: temporarily set to flight page
        FlightScreen(
            uiState.missionsByDate,
            uiState.airports,
            modifier = Modifier
                .padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AviaAssistTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(modifier = modifier,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = dimensionResource(id = R.dimen.padding_small))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plane_icon), contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = buildAnnotatedString {
                        pushStyle(SpanStyle(color = MaterialTheme.colorScheme.primary))
                        append(stringResource(R.string.avia))
                        pop()
                        append(stringResource(R.string.assist))
                    },
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    AviaAssistTopBar()
}

@Composable
fun AviaAssistFab(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onFabClick,
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Default.ContentPasteGo, null)
    }
}

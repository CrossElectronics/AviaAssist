package crosstech.aviaassist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import crosstech.aviaassist.R
import crosstech.aviaassist.components.SnackbarVisualsWithError
import kotlinx.coroutines.launch

@Composable
fun AviaAssistScreen(
    flightViewModel: FlightViewModel = viewModel()
) {
    val clipboardManager = LocalClipboardManager.current
    val uiState by flightViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    flightViewModel.initFlightDataAndAirportMap(LocalContext.current)
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isError = (data.visuals as? SnackbarVisualsWithError)?.isError ?: false
                val buttonColor = if (isError) {
                    ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                } else {
                    ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.inversePrimary
                    )
                }

                Snackbar(
                    modifier = Modifier
                        .padding(12.dp),
                    containerColor = MaterialTheme.colorScheme.error,
                    action = {
                        TextButton(onClick = { data.dismiss() }) {
                            Text("OK", color = MaterialTheme.colorScheme.onError)
                        }
                    }
                ) {
                    Text(data.visuals.message, color = MaterialTheme.colorScheme.onError)
                }
            }
        },
        modifier = Modifier,
        topBar = { AviaAssistTopBar() },
        bottomBar = {},
        floatingActionButton = {
            AviaAssistFab {
                val text = clipboardManager.getText()?.text
                try {
                    if (text != null) {
                        flightViewModel.uploadMissionsGroupedByDate(text)
                    }
                } catch (_: Exception) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            SnackbarVisualsWithError(
                                "无法解析粘贴内容",
                                isError = true
                            )
                        )
                    }
                }
            }
        }
    ) {
        // TODO: temporarily set to flight page
        FlightScreen(
            missionsByDate = uiState.missionsByDate,
            airports = uiState.airports,
            onReplacementStatusChanged = { mission, status ->
                flightViewModel.changeMissionReplacementState(mission, status)
            },
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

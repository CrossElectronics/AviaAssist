package crosstech.aviaassist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AviaAssistScreen(
    flightViewModel: FlightViewModel = viewModel()
) {
    val clipboardManager = LocalClipboardManager.current
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
        val uiState by flightViewModel.uiState.collectAsState()
        // TODO: temporarily set to flight page
        FlightScreen(
            uiState.missionsByDate,
            modifier = Modifier
                .padding(it)
        )
    }
}

@Composable
fun AviaAssistTopBar() {

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

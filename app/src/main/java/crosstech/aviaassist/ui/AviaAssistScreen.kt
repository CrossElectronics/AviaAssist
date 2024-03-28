package crosstech.aviaassist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AviaAssistScreen(){
    Scaffold (
        modifier = Modifier,
        topBar = { AviaAssistTopBar() },
        bottomBar = {},
        floatingActionButton = { AviaAssistFab() }
    ){
        // TODO: temporarily set to flight page
        FlightScreen(
            modifier = Modifier
                .padding(it)
        )
    }
}

@Composable
fun AviaAssistTopBar(){

}

@Composable
fun AviaAssistFab(){

}

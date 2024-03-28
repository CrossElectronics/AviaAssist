package crosstech.aviaassist.ui

import androidx.lifecycle.ViewModel
import crosstech.aviaassist.model.FlightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlightViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FlightViewModel())
    val uiState = _uiState.asStateFlow()


}
package crosstech.aviaassist.ui

import androidx.lifecycle.ViewModel
import crosstech.aviaassist.model.FlightMission
import crosstech.aviaassist.model.FlightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlightViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    fun uploadMissionsGroupedByDate(missionsText: String){
        val missions: List<FlightMission> = FlightMission.parseListFromString(missionsText)
        val groupedMission = missions.groupBy { it.takeoffDateTime.toLocalDate() }
        _uiState.update {
            it.copy(missionsByDate = groupedMission)
        }
    }
}
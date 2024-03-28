package crosstech.aviaassist.model

import java.time.LocalDate

data class FlightUiState(
    val missionsByDate: Map<LocalDate, List<FlightMission>> = mapOf()
)

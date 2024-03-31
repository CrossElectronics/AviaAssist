package crosstech.aviaassist.model

import java.time.LocalDate

data class FlightUiState(
    val missionsByDate: Map<LocalDate, List<FlightMission>> = mapOf(),
    val airports: Map<String, String> = mapOf()
)

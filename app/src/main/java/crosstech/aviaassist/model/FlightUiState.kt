package crosstech.aviaassist.model

import java.time.LocalDate

data class FlightUiState(
    val missionsByDate: Map<LocalDate, List<EvaluatedMission>> = mapOf(),
    val airports: Map<String, String> = mapOf()
)

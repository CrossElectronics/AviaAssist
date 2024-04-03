package crosstech.aviaassist.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

class EvaluatedMission(
    flightNumber: String,
    aircraftReg: String,
    takeoffDateTime: LocalDateTime,
    landingDateTime: LocalDateTime,
    originAirport: String,
    destAirport: String,
    val durationInMinutes: Int,
    val multiplier: Double,
    val isAuthentic: Boolean
) : FlightMission(flightNumber, aircraftReg, takeoffDateTime, landingDateTime, originAirport, destAirport) {
    private var _isReplacement: Boolean = false
    val isReplacement = _isReplacement
    var multiplierConsideringReplacement by mutableDoubleStateOf(multiplier)
    fun setReplacement(isReplacement: Boolean) {
        _isReplacement = isReplacement
        multiplierConsideringReplacement = if (_isReplacement) 0.5 else multiplier
    }
}
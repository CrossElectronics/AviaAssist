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
    private val _multiplier: Double,
    val isAuthentic: Boolean,
    private var _isReplacement: Boolean = false
) : FlightMission(flightNumber, aircraftReg, takeoffDateTime, landingDateTime, originAirport, destAirport){

    var multiplier by mutableDoubleStateOf(_multiplier)
    val isReplacement = _isReplacement
    fun setReplacement(isReplacement: Boolean){
        _isReplacement = isReplacement
        multiplier = if (_isReplacement) 0.5 else _multiplier
    }
}
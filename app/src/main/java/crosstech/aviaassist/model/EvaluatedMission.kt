package crosstech.aviaassist.model

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
) : FlightMission(flightNumber, aircraftReg, takeoffDateTime, landingDateTime, originAirport, destAirport)
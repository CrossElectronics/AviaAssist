package crosstech.aviaassist.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.Duration

data class FlightMission(
    val flightNumber: String,
    val aircraftReg: String,
    val takeoffDateTime: LocalDateTime,
    val landingDateTime: LocalDateTime,
    val originAirport: String,
    val destAirport: String
) {
    fun getDurationAsString(): String {
        val duration = getDuration()
        val hours = duration.toHours()
        val minutes = duration.toMinutes() - 60 * hours

        // Format the duration as HH:mm
        return String.format("%02d:%02d", hours, minutes)
    }

    fun getDuration(): Duration{
        return Duration.between(takeoffDateTime, landingDateTime)
    }
    companion object {
        /**
         * Parses a list of FlightMissions from [missionString] using pb command pasted from user
         */
        fun parseListFromString(missionString: String): List<FlightMission> {
            val flightMissions = mutableListOf<FlightMission>()
            val lines = missionString.lines()

            if (lines.isEmpty()) return flightMissions

            val pattern =
                Regex("""(?<flightNumber>CA\d+)/(?<aircraftReg>B-[A-Z0-9]{4,})\s+(?<originAirport>[A-Z]{3})(?<takeoffDateTime>\d{2}:\d{2})-(?<landingDateTime>\d{2}:\d{2})(?<destAirport>[A-Z]{3})""")

            var currentFlightDate: LocalDate? = null

            for (line in lines) {
                if (line.startsWith("【") && line.contains("】")) {
                    val dateTimeString = line.replace("【", "").replace("】", "")
                    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d")
                    currentFlightDate = LocalDate.parse(dateTimeString, dateTimeFormatter)
                    continue
                }

                val matchResult = pattern.matchEntire(line)
                if (matchResult != null) {
                    val flightNumber = matchResult.groups["flightNumber"]!!.value
                    val aircraftReg = matchResult.groups["aircraftReg"]!!.value
                    val originAirport = matchResult.groups["originAirport"]!!.value
                    val destAirport = matchResult.groups["destAirport"]!!.value
                    val takeoffTime = LocalTime.parse(matchResult.groups["takeoffDateTime"]!!.value)
                    val landingTime = LocalTime.parse(matchResult.groups["landingDateTime"]!!.value)

                    if (currentFlightDate != null) {
                        val takeoffDateTime = LocalDateTime.of(currentFlightDate, takeoffTime)
                        var landingDateTime = LocalDateTime.of(currentFlightDate, landingTime)
                        if (landingDateTime < takeoffDateTime) landingDateTime = landingDateTime.plusDays(1)
                        flightMissions.add(
                            FlightMission(
                                flightNumber, aircraftReg, takeoffDateTime, landingDateTime, originAirport,
                                destAirport
                            )
                        )
                    }
                }
            }

            return flightMissions
        }
    }
}

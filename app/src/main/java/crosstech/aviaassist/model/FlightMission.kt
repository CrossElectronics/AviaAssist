package crosstech.aviaassist.model

import crosstech.aviaassist.data.AIRCRAFT_TYPE_PRIORITY
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.Duration

open class FlightMission(
    val flightNumber: String,
    private val aircraftReg: String,
    val takeoffDateTime: LocalDateTime,
    val landingDateTime: LocalDateTime,
    val originAirport: String,
    val destAirport: String
) {
    fun getDurationAsString(): String {
        val duration = getDuration()
        val hours = duration.toHours()
        val minutes = duration.toMinutes() - 60 * hours
        return String.format("%02d:%02d", hours, minutes)
    }

    infix fun evaluateBy(flightData: List<FlightData>): EvaluatedMission {
        val pickedDatum = flightData
            .filter { it.originAirportCode == this.originAirport && it.destAirportCode == this.destAirport }
            .sortedBy { it.paidTime }
            .sortedBy { AIRCRAFT_TYPE_PRIORITY[it.aircraftType] } // Prioritize 32x and 73x data
        val length = getDuration().toMinutes().toInt()
        // Multiplier calculation
        var multi = 1.0
        if (pickedDatum.isEmpty()) {
            if (length <= 150) multi += 0.2
            // TODO: decide the lowest possible multi for a given airport (difficultyCoeff)
        } else {
            multi = pickedDatum[0].paidTimeMultiplier
        }
        if (this.isRedEye()) multi += 0.3

        if (pickedDatum.isEmpty()) return EvaluatedMission(
            flightNumber,
            aircraftReg,
            takeoffDateTime,
            landingDateTime,
            originAirport,
            destAirport,
            length,
            multi,
            false
        ) else return EvaluatedMission(
            flightNumber,
            aircraftReg,
            takeoffDateTime,
            landingDateTime,
            originAirport,
            destAirport,
            pickedDatum[0].paidTime,
            multi,
            true
        )
    }

    private fun isRedEye(): Boolean {
        val date = takeoffDateTime.toLocalDate()
        val dayBegin = LocalDateTime.of(date, LocalTime.MIDNIGHT)
        val dayEnd = LocalDateTime.of(date.plusDays(1), LocalTime.MIDNIGHT)

        val sevenOClock = LocalDateTime.of(date, LocalTime.of(7, 0))

        return takeoffDateTime > dayBegin && takeoffDateTime < sevenOClock || landingDateTime > dayEnd
    }

    private fun getDuration(): Duration {
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

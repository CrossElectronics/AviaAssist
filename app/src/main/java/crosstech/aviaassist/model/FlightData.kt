package crosstech.aviaassist.model

data class FlightData(
    val originAirport: AirportInfo,
    val destAirport: AirportInfo,
    val aircraftType: String,
    val paidTime: Int,
    val paidTimeMultiplier: Double
) {
    companion object {
        fun parseFromString(flightData: String): FlightData? {
            val split = flightData.split(',')
            return if (split.size != 7) null
            else FlightData(
                originAirport = AirportInfo(split[0], split[1]),
                destAirport = AirportInfo(split[2], split[3]),
                aircraftType = split[4],
                paidTime = split[6].toDouble().toInt(),
                paidTimeMultiplier = split[5].toDouble()
            )
        }
    }
}

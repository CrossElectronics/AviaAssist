package crosstech.aviaassist.model

data class FlightData(
    val originAirportName: String,
    val originAirportCode: String,
    val destAirportName: String,
    val destAirportCode: String,
    val aircraftType: String,
    val paidTime: Int,
    val paidTimeMultiplier: Double
) {
    companion object {
        fun parseFromString(flightData: String): FlightData? {
            val split = flightData.split(',')
            return if (split.size != 7) null
            else FlightData(
                originAirportName = split[0],
                originAirportCode = split[1],
                destAirportName = split[2],
                destAirportCode = split[3],
                aircraftType = split[4],
                paidTime = split[6].toDouble().toInt(),
                paidTimeMultiplier = split[5].toDouble()
            )
        }
    }
}

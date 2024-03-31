package crosstech.aviaassist.model

data class AirportInfo(
    val name: String,
    val code: String,
    val minimumMultipiler: Double = 1.0
) {
}
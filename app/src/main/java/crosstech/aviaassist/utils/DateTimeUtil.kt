package crosstech.aviaassist.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
fun LocalTime.toFormattedString():String {
    return this.format(TIME_FORMAT)
}
fun LocalDate.toFormattedString(): String{
    return this.format(DATE_FORMAT)
}

fun Int.toFormattedString(): String{
    val hour = this / 60
    val min = this - hour * 60
    return String.format("%02d:%02d", hour, min)
}
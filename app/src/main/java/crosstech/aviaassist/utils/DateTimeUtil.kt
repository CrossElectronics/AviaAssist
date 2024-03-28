package crosstech.aviaassist.utils

import java.time.LocalDate
import java.time.LocalDateTime
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
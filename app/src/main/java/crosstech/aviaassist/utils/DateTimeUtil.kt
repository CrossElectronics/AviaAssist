package crosstech.aviaassist.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DateTimeUtil {
    companion object{
        private val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        fun LocalTime.toFormattedString():String {
            return this.format(TIME_FORMAT)
        }
    }
}
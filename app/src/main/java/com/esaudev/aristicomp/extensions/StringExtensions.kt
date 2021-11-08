package com.esaudev.aristicomp.extensions

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): String{
    val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
    val date = formatDate.parse(this)

    val dayOfTheWeek = DateFormat.format("EEEE", date) // Thursday
    val day = DateFormat.format("dd", date)  // 20
    val monthString = DateFormat.format("MMM", date) // Jun

    return "$dayOfTheWeek, $day $monthString"
}

fun String.toTime(): String{
    val formatTime = SimpleDateFormat("HH:mm", Locale("es", "ES"))
    val time = formatTime.parse(this)

    val clock = DateFormat.format("hh:mm a", time)
    return clock.toString()
}
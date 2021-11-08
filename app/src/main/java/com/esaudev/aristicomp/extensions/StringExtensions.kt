package com.esaudev.aristicomp.extensions

import android.text.format.DateFormat
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

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

fun String.hasNotPassed(): Boolean{
    val utcDate = this.getDateFromString()
    val todayDate = Calendar.getInstance()
    val today = todayDate.time
    Log.d("TAG_ESAU_TODAY", today.toString())
    Log.d("TAG_ESAU_UTC", utcDate.toString())
    return utcDate > today
}

fun String.getDateFromString(): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.parse(this)?: Date()
}
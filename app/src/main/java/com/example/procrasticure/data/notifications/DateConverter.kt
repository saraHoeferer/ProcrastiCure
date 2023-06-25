package com.example.procrasticure.data.notifications

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertStringToDate(dateString: String): Date? {
    val format = SimpleDateFormat("yyyy/mm/dd", Locale.getDefault())
    return try {
        format.parse(dateString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun convertDateToString(date: Date): String? {
    val format = SimpleDateFormat("yyyy/mm/dd", Locale.getDefault())
    return format.format(date)
}

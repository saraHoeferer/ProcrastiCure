package com.example.procrasticure.Timer

import java.util.concurrent.TimeUnit

object Utility {

    // time to countdown - 1hr - 60secs
    const val TIME_COUNTDOWN: Long = 60000L*15
    private const val TIME_FORMAT = "%02d:%02d"

    // convert time to milli seconds
    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )
}
package com.example.taskplanner.util

import android.os.Bundle
import com.example.taskplanner.util.extensions.DATE_PATTERN
import com.example.taskplanner.util.extensions.HOURS_PATTERN
import com.example.taskplanner.util.extensions.formatDate
import com.example.taskplanner.util.extensions.getHours
import java.text.SimpleDateFormat
import java.util.*

fun bundle(action: Bundle.() -> Unit): Bundle {
    return Bundle().apply(action)
}

fun timerDate(start1: Long, end2: Long): Long {
    val startDate = "${start1.formatDate()} ${System.currentTimeMillis().getHours()}"
    val endDate = "${end2.formatDate()} $END_OF_DAY"
    val sdf = SimpleDateFormat("$DATE_PATTERN $HOURS_PATTERN", Locale.getDefault())
    val start = sdf.parse(startDate)
    val end = sdf.parse(endDate)
    return end!!.time - start!!.time
}

private const val END_OF_DAY = "23:59:00"
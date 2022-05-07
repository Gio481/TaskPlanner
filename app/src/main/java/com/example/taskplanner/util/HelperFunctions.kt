package com.example.taskplanner.util

import android.os.Bundle
import com.example.taskplanner.util.extensions.formatDate
import com.example.taskplanner.util.extensions.getHours
import java.text.SimpleDateFormat
import java.util.*

fun bundle(action: Bundle.() -> Unit): Bundle {
    return Bundle().apply(action)
}

fun timerDate(start1: Long, end2: Long): Long {
    val startDate = "${start1.formatDate()} ${System.currentTimeMillis().getHours()}"
    val endDate = "${end2.formatDate()} 23:59:00"
    val sdf = SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.getDefault())
    val start = sdf.parse(startDate)
    val end = sdf.parse(endDate)
    return end!!.time - start!!.time
}

package com.example.taskplanner.util.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Long.getHours(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

fun Long.toEndDate(endDate: Long): String {
    return "${this.formatDate()} - ${endDate.formatDate()}"
}

fun Long.isValidDate(endDate: Long): Boolean {
    return System.currentTimeMillis() in this..endDate
}

fun Long.isValidateWith(projectStartDate: Long, projectEndDate: Long): Boolean {
    return this in projectStartDate..projectEndDate
}

inline fun Long?.update(action: () -> Unit) {
    this?.let { action() }
}
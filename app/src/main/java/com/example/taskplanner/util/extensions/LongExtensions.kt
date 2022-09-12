package com.example.taskplanner.util.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
    return sdf.format(this)
}

fun Long.getHours(): String {
    val sdf = SimpleDateFormat(HOURS_PATTERN, Locale.getDefault())
    return sdf.format(this)
}

fun Long.toEndDate(endDate: Long): String {
    return "${this.formatDate()} - ${endDate.formatDate()}"
}

fun Long.isValidDate(endDate: Long): Boolean {
    return System.currentTimeMillis() in this..endDate
}

fun Long?.isValidateWith(projectStartDate: Long?, projectEndDate: Long?): Boolean {
    return this!! in projectEndDate?.let { projectStartDate?.rangeTo(it) }!!
}

const val DATE_PATTERN = "MMM dd yyyy"
const val HOURS_PATTERN = "HH:mm:ss"
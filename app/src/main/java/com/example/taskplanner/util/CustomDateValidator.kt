package com.example.taskplanner.util

import android.annotation.SuppressLint
import android.os.Parcel
import com.google.android.material.datepicker.CalendarConstraints

@SuppressLint("ParcelCreator")
class CustomDateValidator(
    private val startDate: Long?,
    private val endDate: Long?,
) : CalendarConstraints.DateValidator {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {}

    override fun isValid(date: Long): Boolean {
        return date in startDate!!..endDate!!
    }
}
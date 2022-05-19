package com.example.taskplanner.util.extensions

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

fun View.isVisible(visible: Boolean) {
    this.isVisible = visible
}

fun TextView.setTextColor(context: Context, color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun TextView.setBackgroundColor(context: Context, color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}
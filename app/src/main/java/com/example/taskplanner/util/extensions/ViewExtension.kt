package com.example.taskplanner.util.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.isVisible(visible:Boolean){
    this.isVisible = visible
}
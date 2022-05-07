package com.example.taskplanner.util

import android.os.Bundle

fun bundle(action: Bundle.() -> Unit): Bundle {
    return Bundle().apply(action)
}
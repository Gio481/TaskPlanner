package com.example.taskplanner.util.extensions

inline fun Any?.update(action: () -> Unit) {
    this?.let { action() }
}
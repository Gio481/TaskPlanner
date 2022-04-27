package com.example.taskplanner.util

inline fun <T> fetchData(call: () -> Resources<T>): Resources<T> {
    return try {
        call.invoke()
    } catch (e: Exception) {
        Resources.Error(e.message!!)
    }
}
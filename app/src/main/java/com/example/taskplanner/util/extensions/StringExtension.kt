package com.example.taskplanner.util.extensions

inline fun String?.update(action: () -> Unit) {
    if(!this.isNullOrBlank()){
        action()
    }
}
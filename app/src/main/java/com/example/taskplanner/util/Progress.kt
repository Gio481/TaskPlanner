package com.example.taskplanner.util

import com.example.taskplanner.R

enum class Progress(val value: String, val color: Int) {
    TODO("Todo", R.color.blue_500),
    IN_PROGRESS("In Progress", R.color.purple),
    DONE("Done", R.color.green_500)
}
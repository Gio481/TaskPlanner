package com.example.taskplanner.util

import com.example.taskplanner.R


enum class Status(val value: Int, val color: Int) {
    TODO(R.string.todo_state_text, R.color.blue_500),
    IN_PROGRESS(R.string.in_progress_text, R.color.purple),
    DONE(R.string.done_text, R.color.green_500)
}
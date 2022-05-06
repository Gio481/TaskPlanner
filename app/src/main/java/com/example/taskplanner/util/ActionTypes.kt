package com.example.taskplanner.util

sealed class ActionTypes {
    object Update : ActionTypes()
    object UpdateAll : ActionTypes()
    object Delete : ActionTypes()
    object Description : ActionTypes()
}

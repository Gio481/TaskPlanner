package com.example.taskplanner.util

sealed class ActionTypes {
    object Create : ActionTypes()
    object Update : ActionTypes()
    object Delete : ActionTypes()
}

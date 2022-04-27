package com.example.taskplanner.data.model

data class TaskDto(
    val taskId: String? = null,
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val taskProgress: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
)
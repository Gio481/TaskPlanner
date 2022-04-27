package com.example.taskplanner.domain.model

data class TaskDomain(
    val taskId: String? = null,
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val taskProgress: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
)
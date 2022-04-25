package com.example.taskplanner.domain.model

import com.example.taskplanner.util.Progress

data class TaskDomain(
    val taskId: String? = null,
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val taskProgress: Progress? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)
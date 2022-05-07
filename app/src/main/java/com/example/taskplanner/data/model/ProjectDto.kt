package com.example.taskplanner.data.model

import com.example.taskplanner.util.Progress

data class ProjectDto(
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val projectProgress: Progress? = null,
)
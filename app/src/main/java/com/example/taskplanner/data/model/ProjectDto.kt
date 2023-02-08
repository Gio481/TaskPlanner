package com.example.taskplanner.data.model

import com.example.taskplanner.util.Status

data class ProjectDto(
    val projectId: String? = null,
    val ownerId: String? = null,
    val ownerId2: String? = null,
    val title: String? = null,
    val description: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val projectProgress: Status? = null,
)
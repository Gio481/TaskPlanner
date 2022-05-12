package com.example.taskplanner.domain.model

import com.example.taskplanner.util.Status

data class ProjectDomain(
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val projectProgress: Status? = null,
)
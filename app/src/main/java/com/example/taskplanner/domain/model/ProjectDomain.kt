package com.example.taskplanner.domain.model

import com.example.taskplanner.util.Progress

data class ProjectDomain(
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val projectProgress: Progress? = null,
)
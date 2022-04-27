package com.example.taskplanner.data.model

data class ProjectDto(
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
    val projectProgress: String? = null,
)
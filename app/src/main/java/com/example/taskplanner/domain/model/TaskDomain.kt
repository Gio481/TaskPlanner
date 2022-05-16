package com.example.taskplanner.domain.model

import android.os.Parcelable
import com.example.taskplanner.util.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDomain(
    val taskId: String? = null,
    val projectId: String? = null,
    val ownerId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val taskProgress: Status? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
) : Parcelable
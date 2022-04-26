package com.example.taskplanner.di

import com.example.taskplanner.data.mapper.project.ProjectDataMapper
import com.example.taskplanner.data.mapper.project.ProjectDataMapperImpl
import com.example.taskplanner.data.mapper.task.TaskDataMapper
import com.example.taskplanner.data.mapper.task.TaskDataMapperImpl
import com.example.taskplanner.data.mapper.user.UserDataMapper
import com.example.taskplanner.data.mapper.user.UserDataMapperImpl
import com.example.taskplanner.data.model.ProjectDto
import com.example.taskplanner.data.model.TaskDto
import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.domain.model.ProjectDomain
import com.example.taskplanner.domain.model.TaskDomain
import com.example.taskplanner.domain.model.UserDomain
import org.koin.dsl.module

val dataMapperModule = module {
    single<ProjectDataMapper<ProjectDto, ProjectDomain>> { ProjectDataMapperImpl() }
    single<TaskDataMapper<TaskDto, TaskDomain>> { TaskDataMapperImpl() }
    single<UserDataMapper<UserDto, UserDomain>> { UserDataMapperImpl() }
}
package com.example.taskplanner.di

import com.example.taskplanner.data.repository.auth.AuthRepositoryImpl
import com.example.taskplanner.data.repository.project.ProjectRepositoryImpl
import com.example.taskplanner.data.repository.task.TaskRepositoryImpl
import com.example.taskplanner.domain.repository.auth.AuthRepository
import com.example.taskplanner.domain.repository.project.ProjectRepository
import com.example.taskplanner.domain.repository.task.TaskRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get(), get(), get(), get()) }
    single<TaskRepository> { TaskRepositoryImpl(get(), get(), get(), get()) }
}
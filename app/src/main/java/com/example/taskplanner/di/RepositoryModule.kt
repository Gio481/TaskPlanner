package com.example.taskplanner.di

import com.example.taskplanner.data.repository.auth.signin.SignInRepositoryImpl
import com.example.taskplanner.data.repository.auth.signup.SignUpRepositoryImpl
import com.example.taskplanner.data.repository.home.HomeRepositoryImpl
import com.example.taskplanner.data.repository.project.create.ProjectCreatorRepositoryImpl
import com.example.taskplanner.data.repository.project.details.ProjectDetailsRepositoryImpl
import com.example.taskplanner.data.repository.task.create.TaskCreatorRepositoryImpl
import com.example.taskplanner.data.repository.task.details.TaskDetailsRepositoryImpl
import com.example.taskplanner.domain.repository.auth.signin.SignInRepository
import com.example.taskplanner.domain.repository.auth.signup.SignUpRepository
import com.example.taskplanner.domain.repository.home.HomeRepository
import com.example.taskplanner.domain.repository.project.create.ProjectCreatorRepository
import com.example.taskplanner.domain.repository.project.details.ProjectDetailsRepository
import com.example.taskplanner.domain.repository.task.create.TaskCreatorRepository
import com.example.taskplanner.domain.repository.task.details.TaskDetailsRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<SignInRepository> { SignInRepositoryImpl(get()) }
    single<SignUpRepository> { SignUpRepositoryImpl(get(), get(), get(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }
    single<ProjectDetailsRepository> { ProjectDetailsRepositoryImpl(get(), get(), get(), get()) }
    single<ProjectCreatorRepository> { ProjectCreatorRepositoryImpl(get(), get(), get()) }
    single<TaskDetailsRepository> { TaskDetailsRepositoryImpl(get(), get()) }
    single<TaskCreatorRepository> { TaskCreatorRepositoryImpl(get(), get(), get()) }
}
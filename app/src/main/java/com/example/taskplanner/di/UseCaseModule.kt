package com.example.taskplanner.di

import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCase
import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCaseImpl
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCase
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCaseImpl
import com.example.taskplanner.domain.usecase.home.HomeUseCase
import com.example.taskplanner.domain.usecase.home.HomeUseCaseImpl
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.ProjectDetailsUseCase
import com.example.taskplanner.domain.usecase.project.details.ProjectDetailsUseCaseImpl
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCaseImpl
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get()) }
    single<HomeUseCase> { HomeUseCaseImpl(get()) }
    single<ProjectDetailsUseCase> { ProjectDetailsUseCaseImpl(get()) }
    single<ProjectCreatorUseCase> { ProjectCreatorUseCaseImpl(get()) }
    single<TaskCreatorUseCase> { TaskCreatorUseCaseImpl(get()) }
    single<TaskDetailsUseCase> { TaskDetailsUseCaseImpl(get()) }
}
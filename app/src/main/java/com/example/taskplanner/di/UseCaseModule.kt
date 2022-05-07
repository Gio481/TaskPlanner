package com.example.taskplanner.di

import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCase
import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCaseImpl
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCase
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCaseImpl
import com.example.taskplanner.domain.usecase.home.project.GetProjectsUseCase
import com.example.taskplanner.domain.usecase.home.project.GetProjectsUseCaseImpl
import com.example.taskplanner.domain.usecase.home.user.UserUseCase
import com.example.taskplanner.domain.usecase.home.user.UserUseCaseImpl
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCase
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.project_info.GetProjectInfoUseCase
import com.example.taskplanner.domain.usecase.project.details.project_info.GetProjectInfoUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCase
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCaseImpl
import com.example.taskplanner.domain.usecase.splash.IsUserLoggedUseCase
import com.example.taskplanner.domain.usecase.splash.IsUserLoggedUseCaseImpl
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCaseImpl
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get()) }
    single<EditProjectUseCase> { EditProjectUseCaseImpl(get()) }
    single<GetProjectInfoUseCase> { GetProjectInfoUseCaseImpl(get()) }
    single<SubTasksUseCase> { SubTasksUseCaseImpl(get()) }
    single<ProjectCreatorUseCase> { ProjectCreatorUseCaseImpl(get()) }
    single<TaskCreatorUseCase> { TaskCreatorUseCaseImpl(get()) }
    single<TaskDetailsUseCase> { TaskDetailsUseCaseImpl(get()) }
    single<GetProjectsUseCase> { GetProjectsUseCaseImpl(get()) }
    single<UserUseCase> { UserUseCaseImpl(get(), get()) }
    single<IsUserLoggedUseCase> { IsUserLoggedUseCaseImpl(get()) }
}
package com.example.taskplanner.di

import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCase
import com.example.taskplanner.domain.usecase.auth.signin.SignInUseCaseImpl
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCase
import com.example.taskplanner.domain.usecase.auth.signup.SignUpUseCaseImpl
import com.example.taskplanner.domain.usecase.home.HomeUseCase
import com.example.taskplanner.domain.usecase.home.HomeUseCaseImpl
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCase
import com.example.taskplanner.domain.usecase.project.create.ProjectCreatorUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCase
import com.example.taskplanner.domain.usecase.project.details.edit_project.EditProjectUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.project_info.GetProjectInfoUseCase
import com.example.taskplanner.domain.usecase.project.details.project_info.GetProjectInfoUseCaseImpl
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCase
import com.example.taskplanner.domain.usecase.project.details.subtasks.SubTasksUseCaseImpl
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCase
import com.example.taskplanner.domain.usecase.task.create.TaskCreatorUseCaseImpl
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCase
import com.example.taskplanner.domain.usecase.task.details.TaskDetailsUseCaseImpl
import com.example.taskplanner.domain.usecase.util.GetErrorMessage
import com.example.taskplanner.domain.usecase.util.GetErrorMessageImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetErrorMessage> { GetErrorMessageImpl() }
    single<SignInUseCase> { SignInUseCaseImpl(get(), get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get(), get()) }
    single<HomeUseCase> { HomeUseCaseImpl(get(), get(), get()) }
    single<EditProjectUseCase> { EditProjectUseCaseImpl(get(), get()) }
    single<GetProjectInfoUseCase> { GetProjectInfoUseCaseImpl(get(), get()) }
    single<SubTasksUseCase> { SubTasksUseCaseImpl(get(), get()) }
    single<ProjectCreatorUseCase> { ProjectCreatorUseCaseImpl(get(), get()) }
    single<TaskCreatorUseCase> { TaskCreatorUseCaseImpl(get(), get()) }
    single<TaskDetailsUseCase> { TaskDetailsUseCaseImpl(get(), get()) }
}
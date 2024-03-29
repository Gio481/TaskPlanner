package com.example.taskplanner.di

import com.example.taskplanner.presentation.ui.auth.signin.viewmodel.SignInViewModel
import com.example.taskplanner.presentation.ui.auth.signup.viewmodel.SignUpViewModel
import com.example.taskplanner.presentation.ui.home.viewmodel.HomeVewModel
import com.example.taskplanner.presentation.ui.project.create.viewmodel.ProjectCreatorViewModel
import com.example.taskplanner.presentation.ui.project.details.viewmodel.ProjectDetailsViewModel
import com.example.taskplanner.presentation.ui.splash.viewmodel.SplashViewModel
import com.example.taskplanner.presentation.ui.task.create.viewmodel.TaskCreatorViewModel
import com.example.taskplanner.presentation.ui.task.details.viewmodel.TaskDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { HomeVewModel(get(), get()) }
    viewModel { ProjectDetailsViewModel(get(), get()) }
    viewModel { ProjectCreatorViewModel(get(), get()) }
    viewModel { TaskDetailsViewModel(get()) }
    viewModel { TaskCreatorViewModel(get(), get()) }
}
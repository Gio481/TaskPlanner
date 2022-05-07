package com.example.taskplanner.di

import com.example.taskplanner.presentation.ui.auth.signup.validator.SignUpValidator
import com.example.taskplanner.presentation.ui.auth.signup.validator.SignUpValidatorImpl
import com.example.taskplanner.presentation.ui.project.create.validator.ProjectValidator
import com.example.taskplanner.presentation.ui.project.create.validator.ProjectValidatorImpl
import org.koin.dsl.module

val validatorModule = module {
    single<SignUpValidator> { SignUpValidatorImpl(get()) }
    single<ProjectValidator> { ProjectValidatorImpl() }
}
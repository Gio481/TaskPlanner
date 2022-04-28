package com.example.taskplanner.di

import com.example.taskplanner.data.mapper.ProjectDtoMapper
import com.example.taskplanner.data.mapper.TaskDtoMapper
import com.example.taskplanner.domain.mapper.ProjectDomainMapper
import com.example.taskplanner.domain.mapper.TaskDomainMapper
import com.example.taskplanner.domain.mapper.UserDomainMapper
import org.koin.dsl.module

val dataMapperModule = module {
    single { ProjectDtoMapper() }
    single { TaskDtoMapper() }
    single { UserDomainMapper() }
    single { ProjectDomainMapper() }
    single { TaskDomainMapper() }
}
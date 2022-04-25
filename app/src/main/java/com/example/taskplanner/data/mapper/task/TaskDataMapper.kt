package com.example.taskplanner.data.mapper.task

interface TaskDataMapper<TASK_DTO, TASK_DOMAIN> {
    fun dtoListToDomainList(dto: List<TASK_DTO>): List<TASK_DOMAIN>
    fun dtoToDomain(dto: TASK_DTO):TASK_DOMAIN
    fun domainToDto(domain: TASK_DOMAIN): TASK_DTO
}
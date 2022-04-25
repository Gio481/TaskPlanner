package com.example.taskplanner.data.mapper.project

interface ProjectDataMapper<PROJECT_DTO, PROJECT_DOMAIN> {
    fun dtoListToDomainList(dto: List<PROJECT_DTO>): List<PROJECT_DOMAIN>
    fun dtoToDomain(dto: PROJECT_DTO):PROJECT_DOMAIN
    fun domainToDto(domain: PROJECT_DOMAIN): PROJECT_DTO
}
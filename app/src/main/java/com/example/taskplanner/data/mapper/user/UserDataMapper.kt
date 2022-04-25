package com.example.taskplanner.data.mapper.user

interface UserDataMapper<USER_DTO,USER_DOMAIN> {
    fun domainToDto(domain:USER_DOMAIN):USER_DTO
}
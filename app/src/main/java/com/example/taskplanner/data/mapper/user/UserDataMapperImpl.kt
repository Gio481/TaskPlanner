package com.example.taskplanner.data.mapper.user

import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.domain.model.UserDomain

class UserDataMapperImpl:UserDataMapper<UserDto, UserDomain> {
    override fun domainToDto(domain: UserDomain): UserDto {
        return UserDto(
            id = domain.id,
            name = domain.name,
            email = domain.email,
            password = domain.password,
            job = domain.job,
            profileImage = domain.profileImage
        )
    }
}
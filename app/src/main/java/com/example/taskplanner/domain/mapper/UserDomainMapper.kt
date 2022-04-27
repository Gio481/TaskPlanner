package com.example.taskplanner.domain.mapper

import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.DataMapper

class UserDomainMapper : DataMapper<UserDomain, UserDto> {

    override fun modelMapper(model: UserDomain): UserDto {
        return UserDto(
            id = model.id,
            name = model.name,
            email = model.email,
            password = model.password,
            job = model.job,
            profileImage = model.profileImage
        )
    }
}
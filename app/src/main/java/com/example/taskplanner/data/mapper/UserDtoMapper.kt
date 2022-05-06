package com.example.taskplanner.data.mapper

import com.example.taskplanner.data.model.UserDto
import com.example.taskplanner.domain.model.UserDomain
import com.example.taskplanner.util.DataMapper

class UserDtoMapper : DataMapper<UserDto, UserDomain> {
    override fun modelMapper(model: UserDto): UserDomain {
        return UserDomain(
            id = model.id,
            name = model.name,
            job = model.job,
            email = model.email,
            password = model.password,
            profileImage = model.profileImage
        )
    }
}
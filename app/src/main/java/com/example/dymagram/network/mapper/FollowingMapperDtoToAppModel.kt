package com.example.dymagram.network.mapper

import com.example.dymagram.data.model.following.Following
import com.example.dymagram.network.dto.following_dto.FollowingDto

fun mapFollowingDtoToFollowingModel(dto: FollowingDto): Following {
    return Following(
        dto.email,
        dto.first_name,
        dto.followers,
        dto.following,
        dto.id,
        dto.last_name,
        dto.posts
    )
}

fun mapFollowingModelToFollowingDto(model: Following): FollowingDto {
    return FollowingDto(
        email = model.email,
        first_name = model.first_name,
        followers = model.followers,
        following = model.following,
        id = model.id,
        last_name = model.last_name,
        posts = model.posts
    )
}
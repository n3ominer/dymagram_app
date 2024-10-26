package com.example.dymagram.network.mapper

import com.example.dymagram.data.model.posts.Comment
import com.example.dymagram.data.model.posts.Post
import com.example.dymagram.network.dto.posts_dto.PostDto

fun mapPostDtoToPostModel(dto: PostDto): Post {
    return Post(
        caption = dto.caption,
        comments = dto.commentDtos?.map {
            Comment(
                comment = it.comment,
                timestamp = it.timestamp,
                user_id = it.user_id,
                username = it.username
            )
        } ?: listOf(),
        content = dto.content,
        likes = dto.likes,
        post_id = dto.post_id,
        profile_picture = dto.profile_picture,
        shares = dto.shares,
        timestamp = dto.timestamp,
        user_id = dto.user_id,
        username = dto.username
    )
}
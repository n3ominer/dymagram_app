package com.example.dymagram.network.dto.posts_dto

data class PostDto(
    val caption: String,
    val commentDtos: List<CommentDto>?,
    val content: String,
    val likes: Int,
    val post_id: String,
    val profile_picture: String,
    val shares: Int,
    val timestamp: String,
    val user_id: String,
    val username: String
)
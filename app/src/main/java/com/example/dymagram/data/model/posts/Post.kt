package com.example.dymagram.data.model.posts

data class Post(
    val caption: String,
    val comments: List<Comment>?,
    val content: String,
    val likes: Int,
    val post_id: String,
    val profile_picture: String,
    val shares: Int,
    val timestamp: String,
    val user_id: String,
    val username: String
)
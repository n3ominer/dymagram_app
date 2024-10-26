package com.example.dymagram.data.model.following

data class Following(
    val email: String,
    val first_name: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val last_name: String,
    val posts: Int
)
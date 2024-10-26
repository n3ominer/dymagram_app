package com.example.dymagram.network.dto.following_dto

data class FollowingDto(
    val email: String,
    val first_name: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val last_name: String,
    val posts: Int
)
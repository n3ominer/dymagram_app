package com.example.dymagram.network.dto.story_dto

data class StoryDto(
    val profile_picture: String,
    val story_content: List<StoryContentDto>,
    val user_id: String,
    val username: String
)
package com.example.dymagram.network.dto.story_dto

data class StoryContentDto(
    val timestamp: String,
    val type: String,
    val duration: Int,
    val url: String
)
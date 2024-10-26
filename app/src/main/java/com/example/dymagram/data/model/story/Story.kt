package com.example.dymagram.data.model.story

data class Story(
    val profile_picture: String,
    val story_content: List<StoryContent>,
    val user_id: String,
    val username: String
)
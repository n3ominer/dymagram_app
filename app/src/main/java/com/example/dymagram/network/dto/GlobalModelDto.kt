package com.example.dymagram.network.dto

import com.example.dymagram.network.dto.following_dto.FollowingDto
import com.example.dymagram.network.dto.messages_dto.MessageDto
import com.example.dymagram.network.dto.posts_dto.PostDto
import com.example.dymagram.network.dto.story_dto.StoryDto

data class GlobalModelDto(
    val followings: List<FollowingDto>,
    val messages: List<MessageDto>,
    val posts: List<PostDto>,
    val stories: List<StoryDto>
)
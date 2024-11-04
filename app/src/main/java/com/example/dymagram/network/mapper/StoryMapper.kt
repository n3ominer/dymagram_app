package com.example.dymagram.network.mapper

import com.example.dymagram.data.model.story.Story
import com.example.dymagram.data.model.story.StoryContent
import com.example.dymagram.network.dto.story_dto.StoryDto

fun mapStoryDtoToStoryModel(dto: StoryDto): Story {
    return Story(
        profile_picture = dto.profile_picture,
        user_id = dto.user_id,
        username = dto.username,
        story_content = dto.story_content.map {
            StoryContent(
                timestamp = it.timestamp,
                type = it.type,
                duration = it.duration,
                url = it.url
            )
        }
    )
}
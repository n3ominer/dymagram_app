package com.example.dymagram.network.mapper

import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.network.dto.GlobalModelDto

fun mapGlobalDataDtoToGlobalDataModel(dto: GlobalModelDto): GlobalDataModel {
    return GlobalDataModel(
        followings = dto.followings.map { mapFollowingDtoToFollowingModel(it) },
        posts = dto.posts.map { mapPostDtoToPostModel(it) },
        stories = dto.stories.map { mapStoryDtoToStoryModel(it) },
        messages = dto.messages.map { mapMessageDtoToMessageModel(it) }
    )
}
package com.example.dymagram.network.services

import com.example.dymagram.network.dto.story_dto.StoryDto
import retrofit2.Call
import retrofit2.http.GET

interface FeedStoriesService {

    @GET("n3ominer/dymagram-fake-server/stories")
    fun getAllFeedStories(): Call<List<StoryDto>>
}
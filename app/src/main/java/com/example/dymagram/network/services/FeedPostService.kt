package com.example.dymagram.network.services

import com.example.dymagram.network.dto.posts_dto.PostDto
import retrofit2.Call
import retrofit2.http.GET

interface FeedPostService {

    @GET("n3ominer/dymagram-fake-server/posts")
    fun getAllFeedPosts(): Call<List<PostDto>>
}
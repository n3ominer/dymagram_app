package com.example.dymagram.network.services

import com.example.dymagram.network.dto.following_dto.FollowingDto
import retrofit2.Call
import retrofit2.http.GET

interface FollowingService {

    @GET("n3ominer/dymagram-fake-server/following")
    fun getUsersFollowing(): Call<List<FollowingDto>>
}
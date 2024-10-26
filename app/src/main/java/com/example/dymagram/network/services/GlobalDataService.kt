package com.example.dymagram.network.services

import com.example.dymagram.network.dto.GlobalModelDto
import retrofit2.Call
import retrofit2.http.GET

interface GlobalDataService {

    @GET("n3ominer/dymagram-fake-server/db")
    fun getAllData(): Call<GlobalModelDto>
}
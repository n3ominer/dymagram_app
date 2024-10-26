package com.example.dymagram.network.services


import com.example.dymagram.network.dto.messages_dto.MessageDto
import retrofit2.Call
import retrofit2.http.GET

interface MessagesService {

    @GET("n3ominer/dymagram-fake-server/messages")
    fun getAllMessages(): Call<List<MessageDto>>
}
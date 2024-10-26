package com.example.dymagram.network.dto.messages_dto

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("conversation_id")
    val convId: String,
    val messages: List<MessageXDto>,
    val participants: List<String>
)
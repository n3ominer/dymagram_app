package com.example.dymagram.network.mapper

import com.example.dymagram.data.model.messages.Message
import com.example.dymagram.data.model.messages.MessageX
import com.example.dymagram.network.dto.messages_dto.MessageDto

fun mapMessageDtoToMessageModel(dto: MessageDto): Message {
    return Message(
        dto.convId,
        dto.messages.map {
            MessageX(
                message = it.message,
                sender_id = it.sender_id,
                timestamp = it.timestamp
            )
        },
        dto.participants
    )
}
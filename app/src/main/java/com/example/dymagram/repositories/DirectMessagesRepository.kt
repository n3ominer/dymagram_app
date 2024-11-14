package com.example.dymagram.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.data.model.messages.Message
import com.example.dymagram.network.dto.GlobalModelDto
import com.example.dymagram.network.dto.messages_dto.MessageDto
import com.example.dymagram.network.mapper.mapGlobalDataDtoToGlobalDataModel
import com.example.dymagram.network.mapper.mapMessageDtoToMessageModel
import com.example.dymagram.network.services.GlobalDataService
import com.example.dymagram.network.services.MessagesService
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectMessagesRepository(private val messageService: MessagesService) {

    val messagesData : PublishSubject<List<Message>> = PublishSubject.create()

    fun fetchDirectMessages() {
        val call = messageService.getAllMessages()

        call.enqueue(object : Callback<List<MessageDto>> {
            override fun onResponse(
                call: Call<List<MessageDto>>,
                response: Response<List<MessageDto>>
            ) {
                Log.d("Data from fake server", "${response.body()}")
                val responseBody = response.body()
                this@DirectMessagesRepository.messagesData.onNext(
                    responseBody?.map {
                        mapMessageDtoToMessageModel(it)
                     } ?: listOf()
                )
            }

            override fun onFailure(call: Call<List<MessageDto>>, t: Throwable) {
                // Gestiond e l'erreur
                Log.d("Error from fake server", "Error: ${t.message}")
                this@DirectMessagesRepository.messagesData.onError(t)
            }

        })
    }
}
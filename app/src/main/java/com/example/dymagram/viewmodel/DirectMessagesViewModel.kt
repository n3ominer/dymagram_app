package com.example.dymagram.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dymagram.data.model.messages.Message
import com.example.dymagram.repositories.DirectMessagesRepository
import okhttp3.internal.notify

class DirectMessagesViewModel(private val messageRepository: DirectMessagesRepository): ViewModel() {
    private val _messagesData = MutableLiveData<List<Message>>() // itnerne au VM

    val messagesData : LiveData<List<Message>> get() = _messagesData

    @SuppressLint("CheckResult")
    fun fetchMessagesFromRepo() {
        this.messageRepository.messagesData.subscribe { data ->
            this@DirectMessagesViewModel._messagesData.value = data
        }
        if (this._messagesData.value == null) {
            this.messageRepository.fetchDirectMessages()
        } else {
            this@DirectMessagesViewModel._messagesData.notify()
        }
    }
}
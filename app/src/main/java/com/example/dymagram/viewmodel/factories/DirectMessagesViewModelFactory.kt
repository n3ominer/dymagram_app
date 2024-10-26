package com.example.dymagram.viewmodel.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dymagram.repositories.DirectMessagesRepository
import com.example.dymagram.viewmodel.DirectMessagesViewModel

class DirectMessagesViewModelFactory(
    private val repository: DirectMessagesRepository,
    private val fragment: Fragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectMessagesViewModel::class.java)) {
            return DirectMessagesViewModel(fragment, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
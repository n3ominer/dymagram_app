package com.example.dymagram.viewmodel.factories

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dymagram.repositories.GlobalDataRepository
import com.example.dymagram.viewmodel.HomeFeedViewModel

class HomeFeedViewModelFactory(
    private val repository: GlobalDataRepository,
    private val fragment: Fragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFeedViewModel::class.java)) {
            return HomeFeedViewModel(repository, fragment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
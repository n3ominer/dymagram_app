package com.example.dymagram.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.repositories.GlobalDataRepository


class HomeFeedViewModel(private val globalDataRepository: GlobalDataRepository, private val context: LifecycleOwner): ViewModel() {
    private val _globalData = MutableLiveData<GlobalDataModel>() // itnerne au VM
    private val _error = MutableLiveData<String>()

    val globalData : LiveData<GlobalDataModel> get() = _globalData
    val error : LiveData<String> get() = _error


    fun fetchDataFromFakeServer() {
        this.globalDataRepository.globalData.observe(this.context) { data ->
            this@HomeFeedViewModel._globalData.value = data
        }
        this.globalDataRepository.error.observe(this.context) { data ->
            this._error.value = null
            this@HomeFeedViewModel._error.value = data
        }
        if (this._globalData.value == null) {
            this.globalDataRepository.getAllData()
        }
    }
}
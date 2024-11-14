package com.example.dymagram.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.repositories.GlobalDataRepository


class HomeFeedViewModel(private val globalDataRepository: GlobalDataRepository): ViewModel() {
    private val _globalData = MutableLiveData<GlobalDataModel>() // itnerne au VM
    private val _error = MutableLiveData<String>()

    val globalData : LiveData<GlobalDataModel> get() = _globalData
    val error : LiveData<String> get() = _error


    @SuppressLint("CheckResult")
    fun fetchDataFromFakeServer() {
        this.globalDataRepository.globalData.subscribe({ data ->
            this@HomeFeedViewModel._globalData.value = data
        }, { error ->
            this@HomeFeedViewModel._error.value = error.message
        })
        if (this._globalData.value == null) {
            this.globalDataRepository.getAllData()
        }
    }
}
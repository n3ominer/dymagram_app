package com.example.dymagram.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.repositories.GlobalDataRepository


class HomeFeedViewModel(
    private val globalDataRepository: GlobalDataRepository
,   private val context: LifecycleOwner
): ViewModel() {
    private val _globalData = MutableLiveData<GlobalDataModel>() // itnerne au VM

    val globalData : LiveData<GlobalDataModel> get() = _globalData


    fun fetchDataFromFakeServer() {
        this.globalDataRepository.globalData.observe(this.context) { data ->
            this@HomeFeedViewModel._globalData.value = data
        }
        if (this._globalData.value == null) {
            this.globalDataRepository.getAllData()
        }
    }
}
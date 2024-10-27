package com.example.dymagram.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.network.RetrofitClient
import com.example.dymagram.network.dto.GlobalModelDto
import com.example.dymagram.network.mapper.mapGlobalDataDtoToGlobalDataModel
import com.example.dymagram.network.services.GlobalDataService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalDataRepository {

    private val globalService = RetrofitClient.instance.create(GlobalDataService::class.java)

    private val _globalData = MutableLiveData<GlobalDataModel>() // itnerne au Repository
    private val _error = MutableLiveData<String>() // itnerne au Repository

    val globalData : LiveData<GlobalDataModel> get() = _globalData
    val error : LiveData<String> get() = _error

    fun getAllData() {
        val call = globalService.getAllData()

        call.enqueue(object : Callback<GlobalModelDto>{
            override fun onResponse(
                call: Call<GlobalModelDto>,
                response: Response<GlobalModelDto>
            ) {
                Log.d("Data from fake server", "${response.body()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    this@GlobalDataRepository._globalData.value = responseBody?.let {
                        mapGlobalDataDtoToGlobalDataModel(it)
                    }
                } else {
                    this@GlobalDataRepository._error.value = response.errorBody().toString()
                }
            }

            override fun onFailure(call: Call<GlobalModelDto>, t: Throwable) {
                // Gestiond e l'erreur
                Log.d("Error from fake server", "Error: ${t.message}")

            }

        })
    }


}
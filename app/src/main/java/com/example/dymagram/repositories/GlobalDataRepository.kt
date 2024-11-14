package com.example.dymagram.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.network.dto.GlobalModelDto
import com.example.dymagram.network.mapper.mapGlobalDataDtoToGlobalDataModel
import com.example.dymagram.network.services.GlobalDataService
import io.reactivex.rxjava3.subjects.PublishSubject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalDataRepository(private val globalService: GlobalDataService) {

    val globalData : PublishSubject<GlobalDataModel> = PublishSubject.create()

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
                    this@GlobalDataRepository.globalData.onNext(
                        responseBody?.let {
                            mapGlobalDataDtoToGlobalDataModel(it)
                        }
                    )
                } else {
                    this@GlobalDataRepository.globalData.onError(
                        Throwable(response.errorBody().toString())
                    )
                }
            }

            override fun onFailure(call: Call<GlobalModelDto>, t: Throwable) {
                // Gestiond e l'erreur
                Log.d("Error from fake server", "Error: ${t.message}")
                this@GlobalDataRepository.globalData.onError(t)
            }

        })
    }


}
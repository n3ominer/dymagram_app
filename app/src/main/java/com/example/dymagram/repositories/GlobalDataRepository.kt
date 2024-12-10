package com.example.dymagram.repositories

import android.util.Log
import com.example.dymagram.data.model.GlobalDataModel
import com.example.dymagram.network.dto.GlobalModelDto
import com.example.dymagram.network.mapper.mapGlobalDataDtoToGlobalDataModel
import com.example.dymagram.network.services.GlobalDataService
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import io.reactivex.rxjava3.subjects.PublishSubject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalDataRepository(private val globalService: GlobalDataService) {

    val globalData : PublishSubject<GlobalDataModel> = PublishSubject.create()

    private val remoteDataBase = Firebase.firestore

    fun getAllData() {
        val call = globalService.getAllData()

        call.enqueue(object : Callback<GlobalModelDto>{
            override fun onResponse(
                call: Call<GlobalModelDto>,
                response: Response<GlobalModelDto>
            ) {
                //Log.d("Data from fake server", "${response.body()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    this@GlobalDataRepository.globalData.onNext(
                        responseBody?.let {
                            val globalDataModel = mapGlobalDataDtoToGlobalDataModel(it)
                            saveDataToFirestore(globalDataModel)
                            globalDataModel
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
                //Log.d("Error from fake server", "Error: ${t.message}")
                this@GlobalDataRepository.globalData.onError(t)
            }

        })
    }

    private fun saveDataToFirestore(data: GlobalDataModel) {
        data.posts.forEach {
            val postsData = hashMapOf(
                "user_name" to it.username,
                "user_profile_picture" to "https://robohash.org/${it.user_id}",
                "user_id" to it.user_id,
            )

            remoteDataBase.collection("user_feed_posts")
                .add(postsData)
                .addOnSuccessListener { documentReference ->
                    //Log.d("Firebase Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    //Log.w("Firebase Firestore", "Error adding document", e)
                }
        }
    }


}
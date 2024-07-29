package com.ffandroidproj4.androiddevp4.repository

import com.ffandroidproj4.androiddevp4.model.Dog
import com.ffandroidproj4.androiddevp4.network.ApiService
import com.ffandroidproj4.androiddevp4.network.CatAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository {
    private val apiService: ApiService = CatAPIClient.instance

    fun getDogs(onResult: (List<Dog>?) -> Unit, onError: (Throwable) -> Unit) {
        val call = apiService.getDogs()

        call.enqueue(object : Callback<List<Dog>> {
            override fun onResponse(call: Call<List<Dog>>, response: Response<List<Dog>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onError(Throwable(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<List<Dog>>, t: Throwable) {
                onError(t)
            }

        })
    }

    companion object {
        private val apiService: ApiService = CatAPIClient.instance

        fun getDogs(onResult: (List<Dog>?) -> Unit, onError: (Throwable) -> Unit) {
            val call = apiService.getDogs()

            call.enqueue(object : Callback<List<Dog>> {
                override fun onResponse(call: Call<List<Dog>>, response: Response<List<Dog>>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onError(Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<List<Dog>>, t: Throwable) {
                    onError(t)
                }

            })
        }


    }
}
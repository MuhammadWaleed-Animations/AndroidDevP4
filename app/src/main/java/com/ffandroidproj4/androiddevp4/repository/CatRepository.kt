package com.ffandroidproj4.androiddevp4.repository


import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.network.ApiService
import com.ffandroidproj4.androiddevp4.network.CatAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CatRepository {

        private val apiService: ApiService = CatAPIClient.instance

        public fun getCats(onResult: (List<CatModel>?) -> Unit, onError: (Throwable) -> Unit) {
            val call = apiService.getCats()

            call.enqueue(object : Callback<List<CatModel>> {
                override fun onResponse(call: Call<List<CatModel>>, response: Response<List<CatModel>>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onError(Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
                    onError(t)
                }

            })
        }

    companion object {
        private val apiService: ApiService = CatAPIClient.instance
        fun getCats(onResult: (List<CatModel>?) -> Unit, onError: (Throwable) -> Unit) {
            val call = apiService.getCats()

            call.enqueue(object : Callback<List<CatModel>> {
                override fun onResponse(call: Call<List<CatModel>>, response: Response<List<CatModel>>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onError(Throwable(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
                    onError(t)
                }

            })
        }
    }
}
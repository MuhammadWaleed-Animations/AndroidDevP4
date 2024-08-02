package com.ffandroidproj4.androiddevp4.repository

import android.content.Context
import android.util.Log
import com.ffandroidproj4.androiddevp4.model.Dog
import com.ffandroidproj4.androiddevp4.network.ApiService
import com.ffandroidproj4.androiddevp4.network.CatAPIClient
import com.ffandroidproj4.androiddevp4.preferences.DataStoreManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DogRepository (private val context: Context) {
    private val apiService: ApiService = CatAPIClient.instance
    private val dogsKey = DataStoreManager.getStringKey("dogs")
    private val gson = Gson()

    fun getDogs(onResult: (List<Dog>?) -> Unit, onError: (Throwable) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            // First, try to fetch data locally
            val localData = fetchLocally()
            if (localData != null) {
                onResult(localData)
            } else {
                // If no local data, fetch from API
                fetchDogsFromApi(onResult, onError)
            }
        }
    }

    private fun fetchLocally(): List<Dog>? {
        Log.d("Dog Repo", "fetchLocally ")
        val savedDogsJson = DataStoreManager.getData(context, dogsKey)
        return if (!savedDogsJson.isNullOrEmpty()) {
            deserializeDogs(savedDogsJson)
        } else {
            null
        }
    }

    private fun fetchDogsFromApi(onResult: (List<Dog>?) -> Unit, onError: (Throwable) -> Unit) {
        Log.d("Dog Repo", "fetchDogsFromApi ")
        val call = apiService.getDogs()

        call.enqueue(object : Callback<List<Dog>> {
            override fun onResponse(call: Call<List<Dog>>, response: Response<List<Dog>>) {
                if (response.isSuccessful) {
                    val dogs = response.body()
                    // Save the fetched student data in DataStore
                    CoroutineScope(Dispatchers.IO).launch {
                        val dogsJson = serializeDogs(dogs)
                        DataStoreManager.saveData(context, dogsKey, dogsJson)
                    }
                    onResult(dogs)
                } else {
                    onError(Throwable(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<List<Dog>>, t: Throwable) {
                onError(t)
            }
        })
    }

    private fun serializeDogs(dogs: List<Dog>?): String {
        // Serialize the list of dogs to JSON
        return gson.toJson(dogs)
    }

    private fun deserializeDogs(data: String): List<Dog> {
        // Deserialize the JSON string to a list of dogs
        return gson.fromJson(data, object : TypeToken<List<Dog>>() {}.type)
    }
}


//class DogRepository {
//    private val apiService: ApiService = CatAPIClient.instance
//
//    fun getDogs(onResult: (List<Dog>?) -> Unit, onError: (Throwable) -> Unit) {
//        val call = apiService.getDogs()
//
//        call.enqueue(object : Callback<List<Dog>> {
//            override fun onResponse(call: Call<List<Dog>>, response: Response<List<Dog>>) {
//                if (response.isSuccessful) {
//                    onResult(response.body())
//                } else {
//                    onError(Throwable(response.errorBody()?.string()))
//                }
//            }
//
//            override fun onFailure(call: Call<List<Dog>>, t: Throwable) {
//                onError(t)
//            }
//
//        })
//    }
//
//    companion object {
//        private val apiService: ApiService = CatAPIClient.instance
//
//        fun getDogs(onResult: (List<Dog>?) -> Unit, onError: (Throwable) -> Unit) {
//            val call = apiService.getDogs()
//
//            call.enqueue(object : Callback<List<Dog>> {
//                override fun onResponse(call: Call<List<Dog>>, response: Response<List<Dog>>) {
//                    if (response.isSuccessful) {
//                        onResult(response.body())
//                    } else {
//                        onError(Throwable(response.errorBody()?.string()))
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Dog>>, t: Throwable) {
//                    onError(t)
//                }
//
//            })
//        }
//
//
//    }
//}
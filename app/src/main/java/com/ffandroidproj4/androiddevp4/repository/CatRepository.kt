package com.ffandroidproj4.androiddevp4.repository


import android.content.Context
import android.util.Log
import com.ffandroidproj4.androiddevp4.model.CatModel
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

class CatRepository(private val context: Context) {

    private val apiService: ApiService = CatAPIClient.instance
    private val catsKey = DataStoreManager.getStringKey("cats")
    private val gson = Gson()

    fun getCats(onResult: (List<CatModel>?) -> Unit, onError: (Throwable) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            // First, try to fetch data locally
            val localData = fetchLocally()
            if (localData != null) {
                onResult(localData)
            } else {
                // If no local data, fetch from API
                fetchCatsFromApi(onResult, onError)
            }
        }
    }

    private fun fetchLocally(): List<CatModel>? {
        Log.d("Cat Repo", "fetchLocally: ")
        val savedCatsJson = DataStoreManager.getData(context, catsKey)
        return if (!savedCatsJson.isNullOrEmpty()) {
            deserializeCats(savedCatsJson)
        } else {
            null
        }
    }

    private fun fetchCatsFromApi(onResult: (List<CatModel>?) -> Unit, onError: (Throwable) -> Unit) {
        Log.d("Cat Repo", "fetchCatsFromApi: ")
        val call = apiService.getCats()

        call.enqueue(object : Callback<List<CatModel>> {
            override fun onResponse(call: Call<List<CatModel>>, response: Response<List<CatModel>>) {
                if (response.isSuccessful) {
                    val cats = response.body()
                    // Save the fetched student data in DataStore
                    CoroutineScope(Dispatchers.IO).launch {
                        val catsJson = serializeCats(cats)
                        DataStoreManager.saveData(context, catsKey, catsJson)
                    }
                    onResult(cats)
                } else {
                    onError(Throwable(response.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
                onError(t)
            }
        })
    }

    private fun serializeCats(students: List<CatModel>?): String {
        // Serialize the list of students to JSON
        return gson.toJson(students)
    }

    private fun deserializeCats(data: String): List<CatModel> {
        // Deserialize the JSON string to a list of students
        return gson.fromJson(data, object : TypeToken<List<CatModel>>() {}.type)
    }

}
//
//class CatRepository {
//
//        private val apiService: ApiService = CatAPIClient.instance
//
//        public fun getCats(onResult: (List<CatModel>?) -> Unit, onError: (Throwable) -> Unit) {
//            val call = apiService.getCats()
//
//            call.enqueue(object : Callback<List<CatModel>> {
//                override fun onResponse(call: Call<List<CatModel>>, response: Response<List<CatModel>>) {
//                    if (response.isSuccessful) {
//                        onResult(response.body())
//                    } else {
//                        onError(Throwable(response.errorBody()?.string()))
//                    }
//                }
//
//                override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
//                    onError(t)
//                }
//
//            })
//        }
//
//    companion object {
//        private val apiService: ApiService = CatAPIClient.instance
//        fun getCats(onResult: (List<CatModel>?) -> Unit, onError: (Throwable) -> Unit) {
//            val call = apiService.getCats()
//
//            call.enqueue(object : Callback<List<CatModel>> {
//                override fun onResponse(call: Call<List<CatModel>>, response: Response<List<CatModel>>) {
//                    if (response.isSuccessful) {
//                        onResult(response.body())
//                    } else {
//                        onError(Throwable(response.errorBody()?.string()))
//                    }
//                }
//
//                override fun onFailure(call: Call<List<CatModel>>, t: Throwable) {
//                    onError(t)
//                }
//
//            })
//        }
//    }
//}
package com.ffandroidproj4.androiddevp4.network
import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.model.Dog
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("cats")
    fun getCats(): Call<List<CatModel>>

    @GET("dogs")
    fun getDogs(): Call <List<Dog>>

}
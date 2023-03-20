package com.ob.retrofitcoroutine.service

import com.ob.retrofitcoroutine.model.CityModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CityAPI {
    @GET("NodeJSExamples/main/SimpleServer/cities.json")
    fun getCities(): Call<List<CityModel>> //Sadece Retrofit

    @GET("NodeJSExamples/main/SimpleServer/cities.json")
    suspend fun getData(): Response<List<CityModel>> //Retrofit + Coroutine
}
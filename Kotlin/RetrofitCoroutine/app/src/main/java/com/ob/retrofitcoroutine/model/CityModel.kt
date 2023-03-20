package com.ob.retrofitcoroutine.model

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("lat")
    val lat:Double,
    @SerializedName("lon")
    val lon:Double)
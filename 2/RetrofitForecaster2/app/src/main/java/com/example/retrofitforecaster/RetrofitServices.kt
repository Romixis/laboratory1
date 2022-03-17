package com.example.retrofitforecaster

import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("forecast?&appid=625b6fd5c5be3e9c809460446a1fd3e9&units=metric&lang=ru")
    fun getWeatherList(@Query("q")name: String): Call<DataWeather>
}
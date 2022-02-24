package com.example.retrofitforecaster

import com.example.retrofitforecaster.RetrofitServices
import com.example.retrofitforecaster.RetrofitClient

object Common {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
package com.example.retrofitforecaster

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {

        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()

            with(interceptor){
                setLevel((HttpLoggingInterceptor.Level.BODY))
                redactHeader("Authorization")
                redactHeader("Cookie")
            }

            val loggingInterceptorClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(loggingInterceptorClient)
                .build()
        }
        return retrofit!!
    }
}
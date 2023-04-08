package com.example.weatherappusingopenmeteo.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {
    fun getUpdateInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCityInstance() : Retrofit{

    val apiKey = "Your Api Key"
    return Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", apiKey)
                .build()
            chain.proceed(request)
        }.build())
        .build()
}
}
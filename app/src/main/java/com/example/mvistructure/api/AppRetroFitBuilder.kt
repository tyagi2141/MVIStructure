package com.example.mvistructure.api

import com.example.mvistructure.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Rahul on 01/08/20.
 */
object AppRetroFitBuilder {

    const val BASE_URL: String = "https://open-api.xyz/"

    val retrofitBuilder:Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(LiveDataCallAdapterFactory())
    }
    val apiService: ApiService by lazy{
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}
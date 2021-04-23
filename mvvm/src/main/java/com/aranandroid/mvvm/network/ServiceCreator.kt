package com.aranandroid.mvvm.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceCreator {

    private lateinit var  retrofit:Retrofit

    fun init(url:String){
        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        retrofit = builder.build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}
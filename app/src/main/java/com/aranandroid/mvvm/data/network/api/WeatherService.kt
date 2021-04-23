package com.aranandroid.mvvm.data.network.api

import com.aranandroid.mvvm.data.model.weather.HeWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("api/weather")
    fun getWeather(@Query("cityid") weatherId: String): Call<HeWeather>
    // 网络请求返回一个 Call对象
    @GET("api/bing_pic")
    fun getBingPic(): Call<String>

}
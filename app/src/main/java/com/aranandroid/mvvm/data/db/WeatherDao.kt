package com.aranandroid.mvvm.data.db

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.aranandroid.mvvm.BaseApplication
import com.aranandroid.mvvm.data.model.weather.Weather
import com.google.gson.Gson

class WeatherDao {

    fun cacheWeatherInfo(weather: Weather?) {
        if (weather == null) return
        PreferenceManager.getDefaultSharedPreferences(BaseApplication.context).edit {
            val weatherInfo = Gson().toJson(weather)
            putString("weather", weatherInfo)
        }
    }

    fun getCachedWeatherInfo(): Weather? {
        val weatherInfo = PreferenceManager.getDefaultSharedPreferences(BaseApplication.context).getString("weather", null)
        if (weatherInfo != null) {
            return Gson().fromJson(weatherInfo, Weather::class.java)
        }
        return null
    }

    fun cacheBingPic(bingPic: String?) {
        if (bingPic == null) return
        // SharedPreferences 保存数据
        PreferenceManager.getDefaultSharedPreferences(BaseApplication.context).edit {
            putString("bing_pic", bingPic)
        }
    }

    fun getCachedBingPic(): String? = PreferenceManager.getDefaultSharedPreferences(BaseApplication.context).getString("bing_pic", null)

    private fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        action(editor)
        editor.apply()
    }

}
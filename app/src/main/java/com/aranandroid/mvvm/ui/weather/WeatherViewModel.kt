package com.aranandroid.mvvm.ui.weather

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aranandroid.mvvm.BaseApplication
import com.aranandroid.mvvm.data.WeatherRepository
import com.aranandroid.mvvm.data.model.weather.Weather
import com.aranandroid.mvvm.base.m.BaseViewModel
import com.aranandroid.mvvm.data.db.ExampleDatabase
import com.aranandroid.mvvm.data.network.ExampleNetwork
import kotlinx.coroutines.launch

class WeatherViewModel() : BaseViewModel() {
    private val repository: WeatherRepository = WeatherRepository.getInstance(ExampleDatabase.getWeatherDao(), ExampleNetwork.getInstance())
    var weather = MutableLiveData<Weather>()

    var bingPicUrl = MutableLiveData<String>()

    var refreshing = MutableLiveData<Boolean>()

    var weatherInitialized = MutableLiveData<Boolean>()

    var weatherId = ""

    fun getWeather() {

        launch ({
            weather.value = repository.getWeather(weatherId)
            weatherInitialized.value = true
        }, {
            Toast.makeText(BaseApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
        getBingPic(false)
    }

    fun refreshWeather() {
        refreshing.value = true
        launch ({
            weather.value = repository.refreshWeather(weatherId)
            refreshing.value = false
            weatherInitialized.value = true
        }, {
            Toast.makeText(BaseApplication.context, it.message, Toast.LENGTH_SHORT).show()
            refreshing.value = false
        })
        getBingPic(true)
    }

    fun isWeatherCached() = repository.isWeatherCached()

    fun getCachedWeather() = repository.getCachedWeather()

    fun onRefresh() {
        refreshWeather()
    }

    private fun getBingPic(refresh: Boolean) {
        // viewModelScope.launch 开启协程方法
        launch({
            bingPicUrl.value = if (refresh) repository.refreshBingPic() else repository.getBingPic()
        }, {
            Toast.makeText(BaseApplication.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * 自定义一个launch方法
     * 高阶函数：（参数或者返回值有方法的函数）
     */
    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}
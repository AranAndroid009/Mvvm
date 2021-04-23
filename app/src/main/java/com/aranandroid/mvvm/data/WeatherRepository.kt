package com.aranandroid.mvvm.data

import com.aranandroid.mvvm.data.db.WeatherDao
import com.aranandroid.mvvm.data.model.weather.Weather
import com.aranandroid.mvvm.data.network.ExampleNetwork
import com.aranandroid.mvvm.base.m.BaseRepository
import com.aranandroid.mvvm.ui.weather.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 天气数据工厂类
 * 先获取数据库数据 获取不到获取网络数据
 */
class WeatherRepository private constructor(private val weatherDao: WeatherDao, private val network: ExampleNetwork):
    BaseRepository<WeatherViewModel>() {

    suspend fun getWeather(weatherId: String): Weather {
        var weather = weatherDao.getCachedWeatherInfo()
        if (weather == null) weather = requestWeather(weatherId)
        return weather
    }

    suspend fun refreshWeather(weatherId: String) = requestWeather(weatherId)

    suspend fun getBingPic(): String {
        var url = weatherDao.getCachedBingPic()
        if (url == null) url = requestBingPic()
        return url
    }
    // suspend 声明挂起函数
    suspend fun refreshBingPic() = requestBingPic()

    fun isWeatherCached() = weatherDao.getCachedWeatherInfo() != null

    fun getCachedWeather() = weatherDao.getCachedWeatherInfo()!!

    private suspend fun requestWeather(weatherId: String) = withContext(Dispatchers.IO) {
        val heWeather = network.fetchWeather(weatherId)
        val weather = heWeather.weather!![0]
        weatherDao.cacheWeatherInfo(weather)
        weather
    }


    /**
     * suspend 声明挂起函数
     * withContext(Dispatchers.IO) 切换耗时线程
     * 四类协程
     * runBlocking 阻塞 无返回值
     * launch 非阻塞 无返回值
     * withContext 串行 返回值
     * async 并行 返回值
     */

    private suspend fun requestBingPic() = withContext(Dispatchers.IO) {
        val url = network.fetchBingPic()
        // 保存到数据库
        weatherDao.cacheBingPic(url)
        url
    }

    companion object {

        private lateinit var instance: WeatherRepository

        fun getInstance(weatherDao: WeatherDao, network: ExampleNetwork): WeatherRepository {
            if (!::instance.isInitialized) {
                synchronized(WeatherRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = WeatherRepository(weatherDao, network)
                    }
                }
            }
            return instance
        }

    }

}
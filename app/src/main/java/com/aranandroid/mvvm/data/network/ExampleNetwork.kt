package com.aranandroid.mvvm.data.network

import com.aranandroid.mvvm.data.network.api.PlaceService
import com.aranandroid.mvvm.data.network.api.WeatherService

class ExampleNetwork : BaseNetwork() {

    private val placeService = ServiceCreator.create(PlaceService::class.java)

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun fetchProvinceList() = placeService.getProvinces().await()

    suspend fun fetchCityList(provinceId: Int) = placeService.getCities(provinceId).await()

    suspend fun fetchCountyList(provinceId: Int, cityId: Int) = placeService.getCounties(provinceId, cityId).await()

    suspend fun fetchWeather(weatherId: String) = weatherService.getWeather(weatherId).await()

    suspend fun fetchBingPic() = weatherService.getBingPic().await()

    // 伴生类  静态方法 单列
    companion object {

        private var network: ExampleNetwork? = null

        fun getInstance(): ExampleNetwork {
            if (network == null) {
                synchronized(BaseNetwork::class.java) {
                    if (network == null) {
                        network = ExampleNetwork()
                    }
                }
            }
            return network!!
        }

    }

}
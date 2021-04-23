package com.aranandroid.mvvm.ui

import com.aranandroid.mvvm.base.vm.BaseViewModel
import com.aranandroid.mvvm.data.WeatherRepository
import com.aranandroid.mvvm.data.db.ExampleDatabase
import com.aranandroid.mvvm.data.network.ExampleNetwork

class MainViewModel() : BaseViewModel() {
    private val repository: WeatherRepository = WeatherRepository.getInstance(ExampleDatabase.getWeatherDao(), ExampleNetwork.getInstance())

    fun isWeatherCached() = repository.isWeatherCached()

}
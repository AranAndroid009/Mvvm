package com.aranandroid.mvvm.data.db

/**
 * 案例
 */
object ExampleDatabase {

    private var placeDao: PlaceDao? = null

    private var weatherDao: WeatherDao? = null

    fun getPlaceDao(): PlaceDao {
        if (placeDao == null) {
            placeDao = PlaceDao()
        }
        return placeDao!!
    }

    fun getWeatherDao(): WeatherDao {
        if (weatherDao == null) weatherDao = WeatherDao()
        return weatherDao!!
    }

}
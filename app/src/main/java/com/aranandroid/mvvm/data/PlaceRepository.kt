package com.aranandroid.mvvm.data

import com.aranandroid.mvvm.data.db.PlaceDao
import com.aranandroid.mvvm.data.network.ExampleNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 地方厂库
 * 先获取数据库数据 获取不到获取网络数据
 */
class PlaceRepository private constructor(private val placeDao: PlaceDao, private val network: ExampleNetwork) {

    suspend fun getProvinceList() = withContext(Dispatchers.IO) {
        var list = placeDao.getProvinceList()
        if (list.isEmpty()) {
            list = network.fetchProvinceList()
            placeDao.saveProvinceList(list)
        }
        list
    }

    suspend fun getCityList(provinceId: Int) = withContext(Dispatchers.IO) {
        var list = placeDao.getCityList(provinceId)
        if (list.isEmpty()) {
            list = network.fetchCityList(provinceId)
            list.forEach { it.provinceId = provinceId }
            placeDao.saveCityList(list)
        }
        list
    }

    suspend fun getCountyList(provinceId: Int, cityId: Int) = withContext(Dispatchers.IO) {
        var list = placeDao.getCountyList(cityId)
        if (list.isEmpty()) {
            list = network.fetchCountyList(provinceId, cityId)
            list.forEach { it.cityId = cityId }
            placeDao.saveCountyList(list)
        }
        list
    }

    companion object {

        private var instance: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao, network: ExampleNetwork): PlaceRepository {
            if (instance == null) {
                synchronized(PlaceRepository::class.java) {
                    if (instance == null) {
                        instance = PlaceRepository(placeDao, network)
                    }
                }
            }
            return instance!!
        }

    }

}
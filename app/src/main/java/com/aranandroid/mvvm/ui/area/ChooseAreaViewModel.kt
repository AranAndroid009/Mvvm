package com.aranandroid.mvvm.ui.area

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aranandroid.mvvm.BaseApplication
import com.aranandroid.mvvm.data.PlaceRepository
import com.aranandroid.mvvm.data.model.place.City
import com.aranandroid.mvvm.data.model.place.County
import com.aranandroid.mvvm.data.model.place.Province
import com.aranandroid.mvvm.ui.area.ChooseAreaFragment.Companion.LEVEL_CITY
import com.aranandroid.mvvm.ui.area.ChooseAreaFragment.Companion.LEVEL_COUNTY
import com.aranandroid.mvvm.ui.area.ChooseAreaFragment.Companion.LEVEL_PROVINCE
import com.aranandroid.mvvm.base.vm.BaseViewModel
import com.aranandroid.mvvm.data.db.ExampleDatabase
import com.aranandroid.mvvm.data.network.ExampleNetwork
import kotlinx.coroutines.launch
import java.util.*

class ChooseAreaViewModel() : BaseViewModel() {

    private val repository: PlaceRepository = PlaceRepository.getInstance(ExampleDatabase.getPlaceDao(), ExampleNetwork.getInstance())

    var title = MutableLiveData<String>()

    // 当前水平
    var currentLevel = MutableLiveData<Int>()

    var dataChanged = MutableLiveData<Int>()

    var isLoading = MutableLiveData<Boolean>()

    var areaSelected = MutableLiveData<Boolean>()

    var selectedProvince: Province? = null

    var selectedCity: City? = null

    var selectedCounty: County? = null

    lateinit var provinces: MutableList<Province>

    lateinit var cities: MutableList<City>

    lateinit var counties: MutableList<County>

    val dataList = ArrayList<String>()

    fun getProvinces() {
        currentLevel.value = LEVEL_PROVINCE
        launch {
            provinces = repository.getProvinceList()
            dataList.addAll(provinces.map { it.provinceName })
        }
    }

    private fun getCities() = selectedProvince?.let {
        currentLevel.value = LEVEL_CITY
        launch {
            cities = repository.getCityList(it.provinceCode)
            dataList.addAll(cities.map { it.cityName })
        }
    }

    private fun getCounties() = selectedCity?.let {
        currentLevel.value = LEVEL_COUNTY
        launch {
            counties = repository.getCountyList(it.provinceId, it.cityCode)
            dataList.addAll(counties.map { it.countyName })
        }
    }

    fun onListViewItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when {
            currentLevel.value == LEVEL_PROVINCE -> {
                selectedProvince = provinces[position]
                getCities()
            }
            currentLevel.value == LEVEL_CITY -> {
                selectedCity = cities[position]
                getCounties()
            }
            currentLevel.value == LEVEL_COUNTY -> {
                selectedCounty = counties[position]
                areaSelected.value = true
            }
        }
    }

    fun onBack() {
        if (currentLevel.value == LEVEL_COUNTY) {
            getCities()
        } else if (currentLevel.value == LEVEL_CITY) {
            getProvinces()
        }
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            isLoading.value = true
            dataList.clear()
            block()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        } catch (t: Throwable) {
            t.printStackTrace()
            Toast.makeText(BaseApplication.context, t.message, Toast.LENGTH_SHORT).show()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        }
    }

}
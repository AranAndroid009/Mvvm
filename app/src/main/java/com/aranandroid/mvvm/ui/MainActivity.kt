package com.aranandroid.mvvm.ui

import android.os.Bundle
import com.aranandroid.mvvm.R
import com.aranandroid.mvvm.ui.weather.WeatherActivity
import android.content.Intent
import com.aranandroid.mvvm.base.v.BaseActivity
import com.aranandroid.mvvm.databinding.ActivityMainBinding
import com.aranandroid.mvvm.ui.area.ChooseAreaFragment


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

<<<<<<< HEAD
    //测试
=======
>>>>>>> parent of 5d5a3ef (Update MainActivity.kt)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isWeatherCached()) {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            supportFragmentManager.beginTransaction().replace(binding.container.id, ChooseAreaFragment()).commit()
        }
    }
}

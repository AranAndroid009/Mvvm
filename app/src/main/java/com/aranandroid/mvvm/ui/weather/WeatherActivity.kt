package com.aranandroid.mvvm.ui.weather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import com.aranandroid.mvvm.R
import com.aranandroid.mvvm.databinding.ActivityWeatherBinding
import com.aranandroid.mvvm.base.v.BaseActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title.*

class WeatherActivity : BaseActivity<WeatherViewModel, ActivityWeatherBinding>(R.layout.activity_weather) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
        binding.viewModel = viewModel
        binding.resId = R.color.colorPrimary
        binding.lifecycleOwner = this
        viewModel.weatherId = if (viewModel.isWeatherCached()) {
            viewModel.getCachedWeather().basic.weatherId
        } else {
            intent.getStringExtra("weather_id")
        }
        navButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        viewModel.getWeather()
    }

}
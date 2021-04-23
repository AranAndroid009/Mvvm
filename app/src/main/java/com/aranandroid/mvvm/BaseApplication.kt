package com.aranandroid.mvvm

import android.annotation.SuppressLint
import android.content.Context
import com.aranandroid.mvvm.data.network.ServiceCreator

class BaseApplication : MvvmApplication() {

    override fun onCreate() {
        super.onCreate()
        context = this
        // 初始化网络
        ServiceCreator.init("http://guolin.tech/")

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}
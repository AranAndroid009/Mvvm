package com.aranandroid.mvvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.aranandroid.mvvm.util.ActivityLifeCycleCallBackIml
import com.blankj.utilcode.util.CrashUtils
import org.litepal.LitePal

open class MvvmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 数据库
        LitePal.initialize(this)
        context = this
        // 崩溃日志
        CrashUtils.init(object : CrashUtils.OnCrashListener {
            override fun onCrash(crashInfo: CrashUtils.CrashInfo) {
                crashInfo.addExtraHead("extraKey", "extraValue")
            }
        })

        // 管理Activity生命周期
        registerActivityLifecycleCallbacks(ActivityLifeCycleCallBackIml())
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}
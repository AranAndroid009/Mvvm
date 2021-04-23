package com.aranandroid.mvvm.live

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 创建人：zhipeng.liu
 * 创建时间：2019/4/9 0009 19:46
 */
class EmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, ForgroundService::class.java))
        finish()
    }
}
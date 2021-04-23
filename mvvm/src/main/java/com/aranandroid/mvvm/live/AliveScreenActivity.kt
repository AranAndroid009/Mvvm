package com.aranandroid.mvvm.live

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Gravity

/**
 * 创建人：zhipeng.liu
 * 创建时间：2019/4/9 0009 19:46
 */
class AliveScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "启动包活界面")
        val window = window
        //设置这个act 左上角
        window.setGravity(Gravity.START or Gravity.TOP)
        //宽 高都为1
        val attributes = window.attributes
        attributes.width = 1
        attributes.height = 1
        attributes.x = 0
        attributes.y = 0
        window.attributes = attributes
        AliveManager.Companion.instance.setKeep(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "关闭启动包活界面")
    }

    companion object {
        private const val TAG = "AliveScreenActivity"
    }
}
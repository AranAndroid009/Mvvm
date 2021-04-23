package com.aranandroid.mvvm.live

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log

/**
 * 创建人：zhipeng.liu
 * 创建时间：2019/4/9 0009 19:46
 */
class AliveReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        Log.e(TAG, "receive:$action")
        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
            //关屏 开启1px activity
            AliveManager.Companion.instance.startKeep(context)
        } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {
            AliveManager.Companion.instance.finishKeep()
        }
    }

    companion object {
        private const val TAG = "AliveReceiver"
    }
}
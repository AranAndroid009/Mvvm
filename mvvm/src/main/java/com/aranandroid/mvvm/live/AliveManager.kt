package com.aranandroid.mvvm.live

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import java.lang.ref.WeakReference

/**
 * 创建人：zhipeng.liu
 * 创建时间：2019/4/9 0009 19:46
 */
class AliveManager private constructor() {
    private var mKeepAct: WeakReference<Activity>? = null
    private var keepReceiver: AliveReceiver? = null

    /**
     * 注册 开屏 关屏关公
     *
     * @param context
     */
    fun registerKeep(context: Context) {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        keepReceiver = AliveReceiver()
        context.registerReceiver(keepReceiver, filter)
    }

    /**
     * 反注册广播接收者
     *
     * @param context
     */
    fun unregisterKeep(context: Context) {
        if (null != keepReceiver) {
            context.unregisterReceiver(keepReceiver)
        }
    }

    /**
     * 开启Activity
     * @param context
     */
    fun startKeep(context: Context) {
        val intent =
            Intent(context, AliveScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun finishKeep() {
        if (null != mKeepAct) {
            val activity = mKeepAct!!.get()
            activity?.finish()
            mKeepAct = null
        }
    }

    fun setKeep(keep: AliveScreenActivity) {
        mKeepAct = WeakReference(keep)
    }

    companion object {
        val instance = AliveManager()
    }
}
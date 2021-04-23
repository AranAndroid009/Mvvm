package com.aranandroid.mvvm.live

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aranandroid.mvvm.R
import com.aranandroid.mvvm.live.ForgroundService.InnnerService
import java.util.*

/**
 * 创建人：zhipeng.liu
 * 创建时间：2019/4/9 0009 19:46
 */
class ForgroundService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Debug_service", "开始提权")
        InnnerService.startForegroundSelf(this, this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startService(Intent(this, InnnerService::class.java))
        }
        object : Thread() {
            override fun run() {
                while (true) {
                    SystemClock.sleep(5000)
                    Log.d("Debug_run", "" + Date().time)
                }
            }
        }.start()
    }

    class InnnerService : Service() {
        override fun onCreate() {
            super.onCreate()
            startForegroundSelf(this, this)
            stopSelf()
        }

        override fun onBind(intent: Intent): IBinder? {
            return null
        }

        companion object {
            fun startForegroundSelf(
                context: Context,
                service: Service
            ) {
                val builder =
                    NotificationCompat.Builder(context, "default")
                builder.setSmallIcon(R.mipmap.ic_launcher)
                builder.setContentTitle(null)
                builder.setContentText(null)
                val notificationIntent = Intent(
                    context,
                    EmptyActivity::class.java
                )
                val pendingIntent =
                    PendingIntent.getActivity(context, 0, notificationIntent, 0)
                builder.setContentIntent(pendingIntent)
                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    manager.createNotificationChannel(
                        NotificationChannel(
                            "default",
                            "",
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                    )
                }
                val notification = builder.build()
                service.startForeground(10, notification)
            }
        }
    }
}
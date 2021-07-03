package com.ananananzhuo.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel(this)//一定要有这个渠道，我们默认用这个渠道
        findViewById<Button>(R.id.btn_showsimple_notification).setOnClickListener {
            SimpleNotification().apply {
                showNotification(this@MainActivity)
            }
        }
        findViewById<Button>(R.id.btn_notification_click).setOnClickListener {
            ClickNotification().showNotification(this)
        }
        findViewById<Button>(R.id.btn_addbutton_notification).setOnClickListener {
            ButtonNotification().showBtnNotification(this)
        }
        findViewById<Button>(R.id.btn_progressnotification).setOnClickListener {
            ProgressNotification(this).start()
        }
        findViewById<Button>(R.id.btn_custom_layout).setOnClickListener {
            CustomLayoutNotification().showNotification(this)
        }

    }

    /**
     * 创建渠道
     */
    fun createNotificationChannel(context: Context): NotificationChannel? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "渠道描述"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            return channel
        }
        return null
    }
}

const val channelName = "安安安安卓"
const val channelId = "channelId"
var count = 0
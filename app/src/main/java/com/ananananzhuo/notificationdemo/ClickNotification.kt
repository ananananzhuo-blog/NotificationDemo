package com.ananananzhuo.notificationdemo

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * author  :mayong
 * function:可点击的通知
 * date    :2021/7/3
 **/
class ClickNotification {
    fun showNotification(context: Context) {
        val intent = Intent(context,OnlyShowActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent=PendingIntent.getActivity(context,0,intent,0)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentText("点击通知跳转的一个页面中")
            .setContentTitle("可点击通知")
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.apple))
            .setAutoCancel(true)//设置点击了通知，则通知自动消失
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(context).notify(++count, notification)
    }
}
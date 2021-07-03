package com.ananananzhuo.notificationdemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * author  :mayong
 * function:
 * date    :2021/7/3
 **/
class CustomLayoutNotification {

    fun showNotification(context: Context){
        val remoteViews = RemoteViews(context.packageName, R.layout.item_notification)
        val intent = Intent(context,OnlyShowActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
        remoteViews.setOnClickPendingIntent(R.id.iv_pendingintent_click,pendingIntent)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("这个通知的布局是自定义的")
            .setContentText("安安安安卓")
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.person))
            .setCustomContentView(remoteViews)
            .build()
        NotificationManagerCompat.from(context).notify(count,notification)
    }
}
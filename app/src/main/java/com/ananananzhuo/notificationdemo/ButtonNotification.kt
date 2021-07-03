package com.ananananzhuo.notificationdemo

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.session.MediaSession
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * author  :mayong
 * function:
 * date    :2021/7/3
 **/
class ButtonNotification {
    fun showBtnNotification(context: Context) {
        val intent = Intent(context, OnlyShowActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.apple))
            .setContentText("安安安安卓，北国风光，千里冰封，万里雪飘")
            .setContentTitle("按钮通知")
            .addAction(R.drawable.person, "李白", pendingIntent)
            .addAction(R.drawable.apple, "杜甫", pendingIntent)
            .addAction(R.drawable.apple, "王维", pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(++count, notification)
    }
}
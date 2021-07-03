package com.ananananzhuo.notificationdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * author  :mayong
 * function:
 * date    :2021/7/3
 **/
class SimpleNotification {

    fun showNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, channelName)//这里的渠道名就是你自己想展示通知对应的渠道分组
            .setSmallIcon(R.drawable.apple)//设置状态栏展示的通知样式
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.apple
                )
            )//设置通知中的图标样式
            .setContentTitle("公众号")//设置通知标题
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("本文由 公众号 \"安安安安卓\"作者原创，禁抄袭\n 北国风光，" +
                            "千里冰封，万里雪飘，望长城内外，惟余莽莽，大河上下，顿失滔滔，山舞银蛇，原驰蜡象，欲与天公试比高。" +
                            "须晴日，看银装素裹，分外妖娆")
            )
            .setContentText("安安安安卓")//设置通知正文
            .setChannelId(channelId)//设置通知渠道，这个渠道id必须是和我们创建渠道时候的id对应
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()//设置通知优先级
        NotificationManagerCompat.from(context).notify(++count, notification)
    }


}
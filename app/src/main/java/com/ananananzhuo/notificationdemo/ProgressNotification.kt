package com.ananananzhuo.notificationdemo

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * author  :mayong
 * function:
 * date    :2021/7/3
 **/
class ProgressNotification(val context: Context) {
    private val countdown = object : CountDownTimer(15 * 1000, 1000) {
        private val perdegree = 100 / 15
        var count = 0
        override fun onTick(millisUntilFinished: Long) {
            count++
            showNotification(count * perdegree)//更新进度
        }

        override fun onFinish() {
            showNotification(100)
            count = 0
        }
    }

    /**
     * 启动一个可动的进度条
     */
    fun start() {
        countdown.start()
    }

    private fun showNotification(progress: Int) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.person))
            .setColor(Color.GREEN)
            .setContentTitle("这是个进度标题")

        NotificationManagerCompat.from(context).apply {
            builder.setProgress(100, progress, false)
            builder.setContentText("下载进度 $progress%")
            notify(count, builder.build())
        }
    }
}
package com.bridgelabz.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters


class CartWorker(private val context: Context, param: WorkerParameters) : Worker(context, param) {
    val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
    val NOTIFICATION_CHANNEL = "notification_channel"
    val NOTIFICATION_TITLE = "Cart books"
    val NOTIFICATION_MESSAGE = "Books are in cart"

    override fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification: Notification.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification = Notification.Builder(context, "Cart")
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_MESSAGE)

            notificationManager.notify(1, notification.build())
        } else {
            //TODO("VERSION.SDK_INT < O")
        }



        return Result.success()
    }
}
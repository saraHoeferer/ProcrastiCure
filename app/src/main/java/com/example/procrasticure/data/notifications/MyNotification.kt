package com.example.procrasticure.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.procrasticure.MainActivity
import com.example.procrasticure.R

class MyNotification(var context: Context, var title: String, var msg: String) {
    val channelID: String = "FCM100"
    val channelName: String = "FCMMessage"
    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    fun FireNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel= NotificationChannel(channelID, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

        }
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        notificationBuilder=NotificationCompat.Builder(context,channelID)
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
        notificationBuilder.addAction(R.drawable.ic_launcher_background, "Open Messages", pendingIntent)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(msg)
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100,notificationBuilder.build())
    }
}
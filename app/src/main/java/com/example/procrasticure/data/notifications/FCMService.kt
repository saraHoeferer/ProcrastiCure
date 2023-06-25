package com.example.procrasticure.data.notifications

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    val tag: String = "FCMToken"
    override fun onNewToken(token: String) {
        Log.d(tag, "FCMToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...


        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(tag, "Message data payload: ${remoteMessage.data}")


        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(tag, "Message Notification Body: ${it.body}")
            val notify = MyNotification(applicationContext, it.title.toString(), it.body.toString())
            notify.FireNotification()
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
package com.example.procrasticure.data.notifications

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService () {
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed Token: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Behandle die empfangenen FCM-Nachrichten hier
        if(remoteMessage.data.isNotEmpty()){
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let{
            Log.d(TAG, "Message Notification Body: ${it.body}")
            val notice = MyNotification(applicationContext, it.title.toString(), it.body.toString())
            notice.FireNotification()
        }
    }
}
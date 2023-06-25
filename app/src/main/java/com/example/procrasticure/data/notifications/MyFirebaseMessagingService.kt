package com.example.procrasticure.data.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService () {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Behandle die empfangenen FCM-Nachrichten hier
    }
}
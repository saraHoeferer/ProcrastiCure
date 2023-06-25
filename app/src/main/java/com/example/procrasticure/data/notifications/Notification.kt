package com.example.procrasticure.data.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun FCMMessage(){
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center){
        OutlinedButton(onClick = {
            val notice = MyNotification(context, "FCM", "This is a notification for FCM Testing")
            notice.FireNotification()
        }) {
            Text(text = "Fire Notification", fontSize = 16.sp)
        }
    }
}
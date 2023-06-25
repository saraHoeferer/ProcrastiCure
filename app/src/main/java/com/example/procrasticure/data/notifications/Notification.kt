package com.example.procrasticure.data.notifications

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.procrasticure.R
import java.util.concurrent.TimeUnit

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

fun scheduleGoalExpirationCheck(context: Context) {
    val workManager = WorkManager.getInstance(context)

    // Definiere die Arbeit und ihre Einschränkungen
    val workRequest = PeriodicWorkRequestBuilder<GoalExpirationWorker>(
        1, TimeUnit
            .DAYS // Einmal pro Tag
    ).build()

    // Schedule den WorkRequest
    workManager.enqueueUniquePeriodicWork(
        "goalExpirationCheck",
        ExistingPeriodicWorkPolicy.KEEP, // Behalte den vorhandenen Zeitplan bei
        workRequest
    )
}

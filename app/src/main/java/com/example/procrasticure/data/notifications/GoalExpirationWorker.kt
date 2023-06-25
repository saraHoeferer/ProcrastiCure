package com.example.procrasticure.data.notifications

import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.procrasticure.R
import com.example.procrasticure.data.model.Goal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class GoalExpirationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params){
    override fun doWork(): Result {
        val db = FirebaseFirestore.getInstance()
        val goalsCollection = db.collection("Goals")

        // Aktuelles Datum
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.WEEK_OF_YEAR, +1)
        val oneWeekLaterDate = convertDateToString(calendar.time)
        val fiveDaysLater = calendar.add(Calendar.DAY_OF_MONTH, +5)
        val twoDaysLater = calendar.add(Calendar.DAY_OF_MONTH, +2)

        // Abfrage für ablaufende Ziele
        val expiredGoalsQuery = goalsCollection.whereEqualTo("date", oneWeekLaterDate)

        expiredGoalsQuery.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                // Zielliste durchgehen und Push-Benachrichtigung senden
                val goal = document.toObject(Goal::class.java)
                // sendNotification(goal, context = context)
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "$exception")
        }

        return Result.success()
    }

    private fun sendNotification(goal: Goal, context: Context) {
        // Hier den Code zum Senden einer Push-Benachrichtigung einfügen
        val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Erstelle eine Notification-Builders
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentTitle("Ablauf eines Ziels")
            .setContentText("Das Ziel ${goal.Name} läuft bald ab.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        notificationManager.notify(100, notificationBuilder.build())

    }
}
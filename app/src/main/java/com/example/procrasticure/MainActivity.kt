package com.example.procrasticure


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.procrasticure.data.notifications.MyFirebaseMessagingService
import com.example.procrasticure.navigation.Navigation
import com.example.procrasticure.ui.theme.ProcrastiCureTheme
import com.example.procrasticure.viewModels.BigViewModel

class MainActivity : ComponentActivity() {
    private val sessionViewModel by viewModels<BigViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProcrastiCureTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Navigation(sessionViewModel = sessionViewModel)

                }
            }
        }
    }
}


package com.example.procrasticure


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.procrasticure.navigation.Navigation
import com.example.procrasticure.ui.theme.ProcrastiCureTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth;

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContent {
            ProcrastiCureTheme {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                ) {
                    /*val factoryTimer = TimerViewModelFactory()
                    val timerViewModel: TimerViewModel = viewModel(factory = factoryTimer)*/

                    val currentUser = auth.currentUser
                    //updateUI(currentUser)

                    Navigation()
                }
            }
        }
    }
}


package com.example.procrasticure


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.procrasticure.navigation.Navigation
import com.example.procrasticure.ui.theme.ProcrastiCureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProcrastiCureTheme {
                // A surface container using the 'background' color from the theme

                /*val factoryTimer = TimerViewModelFactory()
                val timerViewModel: TimerViewModel = viewModel(factory = factoryTimer)*/

                Navigation()
            }
        }
    }
}


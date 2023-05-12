package com.example.procrasticure


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procrasticure.Navigation.Navigation
import com.example.procrasticure.ViewModels.TimerViewModel
import com.example.procrasticure.ViewModels.TimerViewModelFactory
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


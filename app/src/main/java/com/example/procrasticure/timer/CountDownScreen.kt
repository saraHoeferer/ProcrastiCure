package com.example.procrasticure.timer


import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procrasticure.R
import com.example.procrasticure.timer.components.CountDownButton
import com.example.procrasticure.timer.components.CountDownIndicator
import com.example.procrasticure.timer.components.ShowCelebration
import com.example.procrasticure.viewModels.TimerViewModel
import com.example.procrasticure.viewModels.TimerViewModelFactory
import java.util.concurrent.TimeUnit


@Composable
fun CountDownView(input: Long) {

    val factoryTimer = TimerViewModelFactory(input)
    val timerViewModel: TimerViewModel = viewModel(factory = factoryTimer)


    val time by timerViewModel.time.observeAsState(input.formatTime())
    val progress by timerViewModel.progress.observeAsState(1.00F)
    val isPlaying by timerViewModel.isPlaying.observeAsState(false)
    val celebrate by timerViewModel.celebrate.observeAsState(false)

    CountDownView(time = time.toString(), progress = progress, isPlaying = isPlaying, celebrate = celebrate) {
        timerViewModel.handleCountDownTimer()
    }
}

const val TIME_FORMAT = "%02d:%02d:%02d"

fun Long.formatTime(): String {
    return String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toHours(this ),
        TimeUnit.MILLISECONDS.toMinutes(this) % 60,
        TimeUnit.MILLISECONDS.toSeconds(this) % 60)
}


@Composable
fun CountDownView(
    time: String,
    progress: Float,
    isPlaying: Boolean,
    celebrate: Boolean,
    optionSelected: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        if (celebrate) {
            ShowCelebration()
        }

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            CountDownIndicator(
                Modifier.padding(top = 50.dp),
                progress = progress,
                time = time,
                size = 300,
                stroke = 12
            )

            CountDownButton(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(70.dp),
                isPlaying = isPlaying
            ) {
                optionSelected()
            }
        }
    }
}

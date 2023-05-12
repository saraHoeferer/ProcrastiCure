package com.example.procrasticure.Timer


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procrasticure.R
import com.example.procrasticure.Timer.Components.CountDownButton
import com.example.procrasticure.Timer.Components.CountDownIndicator
import com.example.procrasticure.Timer.Components.ShowCelebration
import com.example.procrasticure.ViewModels.TimerViewModel
import com.example.procrasticure.ViewModels.TimerViewModelFactory
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

const val TIME_FORMAT = "%02d:%02d"

fun Long.formatTime(): String {
    return String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (celebrate) {
            ShowCelebration()
        }

        Text(
            text = "Timer",
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            fontFamily = FontFamily(Font(R.font.poppins_semibold))

        )

        /*Text(
            text = "1 minute to launch...",
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            fontFamily = FontFamily(Font(R.font.poppins_semibold))
        )*/

        Text(
            text = "Click to start or stop countdown",
            color = androidx.compose.ui.graphics.Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )

        CountDownIndicator(
            Modifier.padding(top = 50.dp),
            progress = progress,
            time = time,
            size = 250,
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

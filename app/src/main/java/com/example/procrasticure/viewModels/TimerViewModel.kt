package com.example.procrasticure.viewModels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.timer.SingleLiveEvent
import com.example.procrasticure.timer.formatTime
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TimerViewModel(
    private val input: Long,
    private val userViewModel: UserViewModel,
    private val sessionViewModel: BigViewModel
) : ViewModel() {

    //region Properties
    private var countDownTimer: CountDownTimer? = null
    //endregion

    //region States
    private val _time = MutableLiveData(input.formatTime())
    val time: MutableLiveData<String> = _time

    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    // hold data for celebrate view as boolean

    // private
    private val _celebrate = SingleLiveEvent<Boolean>()

    // accessed publicly
    val celebrate: LiveData<Boolean> get() = _celebrate

    //endregion

    //region Public methods
    fun handleCountDownTimer() {
        if (isPlaying.value == true) {
            pauseTimer()
            _celebrate.postValue(false)
        } else {
            startTimer()
        }
    }
    //endregion

    //region Private methods
    private fun pauseTimer() {
        countDownTimer?.cancel()
        println(progress.value)
        println(input)
        val timeSpent = (input - input * progress.value!!.toFloat()) / 60000f
        if (timeSpent > 1.0f || progress.value!! < 0.1f) {
            println("hier")
            val collectedPoints: Int
            if (progress.value!! < 0.1f) {
                collectedPoints = (input / 60000L).toFloat().roundToInt() * 100
                println(collectedPoints)
                viewModelScope.launch {
                    userViewModel.givePointstoUser(
                        sessionViewModel,
                        collectedPoints.toLong()
                    )
                }
            } else {
                collectedPoints = timeSpent.roundToInt() * 100
                println(collectedPoints)
                viewModelScope.launch {
                    userViewModel.givePointstoUser(
                        sessionViewModel,
                        collectedPoints.toLong()
                    )
                }
            }
        }
        handleTimerValues(false, input.formatTime(), 1.0F, false)
    }

    private fun startTimer() {

        _isPlaying.value = true
        countDownTimer = object : CountDownTimer(input, 1000) {

            override fun onTick(millisRemaining: Long) {
                val progressValue = millisRemaining.toFloat() / input
                handleTimerValues(true, millisRemaining.formatTime(), progressValue, false)
                _celebrate.postValue(false)
            }

            override fun onFinish() {
                pauseTimer()
                _celebrate.postValue(true)
            }
        }.start()
    }

    private fun handleTimerValues(
        isPlaying: Boolean,
        text: String,
        progress: Float,
        celebrate: Boolean
    ) {
        _isPlaying.value = isPlaying
        _time.value = text
        _progress.value = progress
        _celebrate.postValue(celebrate)
    }
    //endregion
}

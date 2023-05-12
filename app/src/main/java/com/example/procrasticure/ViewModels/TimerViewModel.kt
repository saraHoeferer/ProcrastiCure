package com.example.procrasticure.ViewModels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procrasticure.Timer.SingleLiveEvent
import com.example.procrasticure.Timer.Utility
import com.example.procrasticure.Timer.formatTime

class TimerViewModel(private val input: Long) : ViewModel() {

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
        handleTimerValues(false, input.formatTime(), 1.0F, false)
    }

    private fun startTimer() {

        _isPlaying.value = true
        countDownTimer = object : CountDownTimer(input.toLong(), 1000) {

            override fun onTick(millisRemaining: Long) {
                val progressValue = millisRemaining.toFloat() / input.toLong()
                handleTimerValues(true, millisRemaining.formatTime(), progressValue, false)
                _celebrate.postValue(false)
            }

            override fun onFinish() {
                pauseTimer()
                _celebrate.postValue(true)
            }
        }.start()
    }

    private fun handleTimerValues(isPlaying: Boolean, text: String, progress: Float, celebrate: Boolean) {
        _isPlaying.value = isPlaying
        _time.value = text
        _progress.value = progress
        _celebrate.postValue(celebrate)
    }
    //endregion
}

package com.example.procrasticure.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TimerViewModelFactory(private val input: Long): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(input) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
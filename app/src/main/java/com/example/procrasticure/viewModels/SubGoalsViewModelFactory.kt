package com.example.procrasticure.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubGoalsViewModelFactory(private val id: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubGoalsViewModel::class.java)){
            return SubGoalsViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }

}
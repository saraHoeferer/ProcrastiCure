package com.example.procrasticure.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.procrasticure.data.model.User

class BigViewModel : ViewModel() {
    var user by mutableStateOf(User("", 0, false, null))
}
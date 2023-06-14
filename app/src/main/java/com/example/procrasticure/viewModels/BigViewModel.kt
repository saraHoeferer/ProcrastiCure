package com.example.procrasticure.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class BigViewModel: ViewModel() {
    var currentUserId: FirebaseUser? = null
    var isLoggedIn by mutableStateOf(false)

    suspend fun signIn(){
        isLoggedIn = true
    }

    suspend fun singOut(){
        isLoggedIn = false
    }
}
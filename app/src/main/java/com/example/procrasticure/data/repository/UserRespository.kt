package com.example.procrasticure.data.repository

import com.example.procrasticure.data.State
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow


interface UserRespository{
    val auth: FirebaseAuth

    suspend fun signUpUser(email: String, password: String): Flow<State<AuthResult>>
}
package com.example.procrasticure.data.repository

import com.example.procrasticure.data.State
import com.example.procrasticure.viewModels.BigViewModel
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow


interface UserRespository{
    val auth: FirebaseAuth

    suspend fun signUpUser(email: String, password: String, sessionViewModel: BigViewModel): Flow<State<AuthResult>>

    suspend fun signInUser(email: String, password: String, sessionViewModel: BigViewModel): Flow<State<AuthResult>>
}
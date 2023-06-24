package com.example.procrasticure.data.repository

import android.content.Context
import androidx.compose.runtime.CompositionLocalContext
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

    suspend fun deleteUser(sessionViewModel: BigViewModel)

    suspend fun editEmail(email: String, sessionViewModel: BigViewModel, context: Context)

    suspend fun editPassword(password: String, sessionViewModel: BigViewModel, context: Context)

    suspend fun logOut(sessionViewModel: BigViewModel)

    suspend fun getPoints(sessionViewModel: BigViewModel)

    suspend fun givePointsToUser(sessionViewModel: BigViewModel, points:Long)
}
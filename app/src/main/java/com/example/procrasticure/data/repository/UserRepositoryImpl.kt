package com.example.procrasticure.data.repository

import com.example.procrasticure.data.State
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(): UserRespository {
    override val auth = Firebase.auth

    override suspend fun signUpUser(email: String, password: String) = flow{
        emit(State.loading())
        auth.createUserWithEmailAndPassword(email, password). await().run {
            emit(State.success(this))
        }
    }.catch {
        emit(State.error(it.message ?: UNKNOWN_ERROR))
    }

    companion object{
        const val UNKNOWN_ERROR = "An unknown error occurred. Please try again later."
    }
}
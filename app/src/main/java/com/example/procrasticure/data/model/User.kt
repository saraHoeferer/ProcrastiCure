package com.example.procrasticure.data.model

import com.google.firebase.auth.FirebaseUser

data class User(
    private var Id: String? = "",
    private var Points: Long? = 0,
    private var LoggedIn: Boolean? = false,
    private var FirebaseUser: FirebaseUser?
) {
    fun getId(): String? {
        return this.Id
    }

    fun setId(id: String) {
        this.Id = id
    }

    fun getPoints(): Long? {
        return this.Points
    }

    fun setPoints(points: Long) {
        this.Points = points
    }

    fun getLoggedIn(): Boolean? {
        return this.LoggedIn
    }

    fun setLoggedIn(loggedIn: Boolean) {
        this.LoggedIn = loggedIn
    }

    fun getFirebaseUser(): FirebaseUser? {
        return this.FirebaseUser
    }

    fun setFirebaseUser(firebaseUser: FirebaseUser?) {
        this.FirebaseUser = firebaseUser
    }


}
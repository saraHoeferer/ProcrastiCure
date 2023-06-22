package com.example.procrasticure.data.repository

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import com.example.procrasticure.data.State
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.viewModels.BigViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(): UserRespository {
    override val auth = Firebase.auth
    private val database = FirebaseFirestore.getInstance()

    override suspend fun signUpUser(email: String, password: String, sessionViewModel: BigViewModel) = flow{
        emit(State.loading())
        auth.createUserWithEmailAndPassword(email, password). await().run {
            emit(State.success(this))
            sessionViewModel.user.setId(FirebaseAuth.getInstance().currentUser!!.uid)
            sessionViewModel.user.setFirebaseUser(FirebaseAuth.getInstance().currentUser)
            sessionViewModel.user.setLoggedIn(true)
        }
    }.catch {
        emit(State.error(it.message ?: UNKNOWN_ERROR))
    }

    override suspend fun signInUser(email: String, password: String, sessionViewModel: BigViewModel)= flow {
        emit(State.loading())
        auth.signInWithEmailAndPassword(email, password).await().run{
            emit(State.success(this))
            sessionViewModel.user.setId(FirebaseAuth.getInstance().currentUser!!.uid)
            sessionViewModel.user.setFirebaseUser(FirebaseAuth.getInstance().currentUser)
            sessionViewModel.user.setLoggedIn(true)
            getPoints(sessionViewModel)

        }

    }.catch{
        emit(State.error(it.message ?: UNKNOWN_ERROR))
    }

    override suspend fun deleteUser(sessionViewModel: BigViewModel) {
        auth.currentUser?.delete()?.addOnSuccessListener{
            sessionViewModel.user.setId("")
            sessionViewModel.user.setFirebaseUser(null)
            sessionViewModel.user.setLoggedIn(false)
            println("worked delete")
        }?.addOnFailureListener{
            println("didnt delete")
        }
    }

    override suspend fun editEmail(email: String, sessionViewModel: BigViewModel, context: Context){
        auth.currentUser?.updateEmail(email)?.addOnSuccessListener{
            sessionViewModel.user.setId(auth.currentUser!!.uid)
            sessionViewModel.user.setFirebaseUser(auth.currentUser)
            Toast.makeText(context, "Your Email was successfully changed", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener{
            Toast.makeText(context, "Your Email could not be changed", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun editPassword(password: String, sessionViewModel: BigViewModel, context: Context){
        auth.currentUser?.updatePassword(password)?.addOnSuccessListener{
            sessionViewModel.user.setId(auth.currentUser!!.uid)
            sessionViewModel.user.setFirebaseUser(auth.currentUser)
            Toast.makeText(context, "Your Password was successfully changed", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener{
            Toast.makeText(context, "Your Password could not be changed", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun logOut(sessionViewModel: BigViewModel) {
        auth.signOut()
        sessionViewModel.user.setId("")
        sessionViewModel.user.setFirebaseUser(null)
        sessionViewModel.user.setLoggedIn(false)
    }

    override suspend fun getPoints(sessionViewModel: BigViewModel) {
        database.collection("Users").document(sessionViewModel.user.getId()!!)
            .get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                println(queryDocumentSnapshot.data?.get("points"))
                println(queryDocumentSnapshot.get("value"))

                    val points = queryDocumentSnapshot.data?.get("points")
                    if (points != null) {
                        sessionViewModel.user.setPoints(points as Long)
                    }
                println("worked")
            }
            .addOnFailureListener{ println("no points") }
    }

    companion object{
        const val UNKNOWN_ERROR = "An unknown error occurred. Please try again later."
    }
}
package com.example.procrasticure.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.model.SubGoal
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent


@Suppress("UNCHECKED_CAST")
class GoalsViewModel() : ViewModel() {
    private val database = FirebaseFirestore.getInstance()

    // producer = responsible for updating MutableStateFlow
    private val _goals = mutableStateListOf<Goal?>()
    // consumer = all classes collecting from StateFlow
    var goals: List<Goal?> = _goals

    init {
        Log.d(TAG, "init is started")
        getGoals()
    }

    private fun getGoals(){
        database.collection("Goals").get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    for (d in list) {
                        var c: Goal? = d.toObject(Goal::class.java)
                        if (c != null) {
                            c.Id = d.id
                        }
                        _goals.add(c)
                    }
                } else {
                    println("No Goals")
                }
            }
            .addOnFailureListener{
                println("Failure")
            }
    }


}
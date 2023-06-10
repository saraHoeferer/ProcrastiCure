package com.example.procrasticure.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.procrasticure.data.model.Goal
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent


class GoalsViewModel() : ViewModel() {
    private val database = Firebase.database

    // producer = responsible for updating MutableStateFlow
    private val _goals = mutableStateOf<List<Goal>>(emptyList())
    // consumer = all classes collecting from StateFlow
    val goals: State<List<Goal>> = _goals

    init {
        Log.d(TAG, "init is started")
        getGoals()
    }

    private fun getGoals(){
        database.getReference("Goals")
            .addValueEventListener(
                object: ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val goals = dataSnapshot.getValue<HashMap<String, Any>>()
                        println(goals)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }

                }
            )
    }


}
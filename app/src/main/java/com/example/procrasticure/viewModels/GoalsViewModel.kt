package com.example.procrasticure.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procrasticure.data.model.Goal
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("UNCHECKED_CAST")
class GoalsViewModel() : ViewModel() {
    private val database = FirebaseFirestore.getInstance()
    private var _goals : MutableLiveData<ArrayList<Goal>> = MutableLiveData<ArrayList<Goal>>()

    val goals: ArrayList<Goal> = ArrayList()

    init {
        Log.d(TAG, "init is started")
        getGoals()
        listenToChanges()
    }

    private fun getGoals(){
        database.collection("Goals").get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    goals.clear() // without: would expand list all the time
                    for (document in list) {
                        val goal: Goal? = document.toObject(Goal::class.java)
                        if (goal != null) {
                            goal.setId(document.id)
                            goals.add(goal)
                        }

                    }
                    _goals.value = goals
                } else {
                    println("No Goals")
                }
            }
            .addOnFailureListener{
                println("Failure")
            }
    }

    private fun listenToChanges(){
        val docRef = database.collection("Goals")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val documents = snapshot.documentChanges

                for (change in documents) {
                    val goal: Goal = change.document.toObject(Goal::class.java)
                    goal.setId(change.document.id)
                    val oldGoal = getGoalById(goal.getId()!!)
                    if (oldGoal != null){
                        goals.remove(oldGoal)
                    }
                    goals.add(goal)
                }

                _goals.value = goals

            }
        }
    }

    fun getGoalById(id: String): Goal?{
        for (goal in goals){
            if (goal.getId() == id){
                return goal
            }
        }
        return null
    }

}
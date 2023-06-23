package com.example.procrasticure.viewModels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.model.SubGoal
import com.google.firebase.firestore.FirebaseFirestore

class SubGoalsViewModel(id: String) : ViewModel() {
    private val id = id
    private val database = FirebaseFirestore.getInstance()
    private var _subGoals : MutableLiveData<ArrayList<SubGoal>> = MutableLiveData<ArrayList<SubGoal>>()

    val subGoals: ArrayList<SubGoal> = ArrayList()

    init {
        Log.d(ContentValues.TAG, "init is started")
        getSubGoals()
        listenToChanges()
    }
    private fun getSubGoals(){
        var docRef = database.collection("Goals/$id/SubGoals")
        docRef.get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    subGoals.clear() // without: would expand list all the time
                    for (document in list) {
                        val subgoal: SubGoal? = document.toObject(SubGoal::class.java)
                        if (subgoal != null) {
                            subgoal.setId(document.id)
                            subGoals.add(subgoal)
                        }

                    }
                    _subGoals.value = subGoals
                } else {
                    println("No Goals")
                }
            }
            .addOnFailureListener{
                println("Failure")
            }
    }
    private fun listenToChanges(){
        val docRef = database.collection("Goals/$id/SubGoals")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val documents = snapshot.documentChanges
                //Wenns wirklich nur changes sind - jetzt spielt die ganze subgoal liste einfach nochmal rein
                subGoals.clear()
                for (change in documents) {
                    val subgoal: SubGoal = change.document.toObject(SubGoal::class.java)
                    subgoal.setId(change.document.id)
                    subGoals.add(subgoal)
                }

                _subGoals.value = subGoals

            }
        }
    }



}
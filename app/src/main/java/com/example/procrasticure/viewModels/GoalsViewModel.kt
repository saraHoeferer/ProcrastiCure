package com.example.procrasticure.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.GoalRepositoryImpl
import com.example.procrasticure.data.repository.UserRespository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import javax.inject.Inject


class GoalsViewModel @Inject constructor(private val sessionViewModel: BigViewModel, private val goalRepositoryImpl: GoalRepositoryImpl): ViewModel() {
    private val database = FirebaseFirestore.getInstance()
    private var _goals : MutableLiveData<ArrayList<Goal>> = MutableLiveData<ArrayList<Goal>>()

    val goals: ArrayList<Goal> = ArrayList()

    init {
        Log.d(TAG, "init is started")
        viewModelScope.launch {
            getGoals(sessionViewModel)
            listenToChanges(sessionViewModel)
        }
    }

    suspend fun getGoals(sessionViewModel: BigViewModel){
        _goals.value = goalRepositoryImpl.getGoals(sessionViewModel = sessionViewModel, goalArrayList = goals)
    }

    suspend fun listenToChanges(sessionViewModel: BigViewModel){
        _goals.value = goalRepositoryImpl.listenToChange(sessionViewModel = sessionViewModel, goalArrayList = goals)
    }

    suspend fun finishGoal(goalId: String, sessionViewModel: BigViewModel){
        goalRepositoryImpl.finishGoal(goalId = goalId, sessionViewModel = sessionViewModel)
    }

    suspend fun addGoal(goal: Goal, context: Context){
        goalRepositoryImpl.addGoal(goal = goal, context = context)
    }

    fun sortGoalsAlphabetically(){
        database.collection("Goals").orderBy("name", Query.Direction.ASCENDING).get()
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

    fun sortGoalsByDeadlineAscending(){
        database.collection("Goals").orderBy("date", Query.Direction.ASCENDING).get()
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

    fun sortGoalsByDeadlineDescending(){
        database.collection("Goals").orderBy("date", Query.Direction.DESCENDING).get()
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


}
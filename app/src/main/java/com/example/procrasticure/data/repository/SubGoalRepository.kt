package com.example.procrasticure.data.repository

import android.content.Context
import com.example.procrasticure.data.model.Goal
import com.google.firebase.firestore.FirebaseFirestore

interface SubGoalRepository {
    val database: FirebaseFirestore

    suspend fun getSubGoals(goalId: String, subGoalList: ArrayList<Goal>): ArrayList<Goal>

    suspend fun listenToChanges(goalId: String, subGoalList: ArrayList<Goal>): ArrayList<Goal>

    suspend fun addSubGoal(goalId: String, subGoal: Goal, context: Context)

    suspend fun checkSubGoal(goalId: String, subGoalId: String, goalPoints: Long): Long

    suspend fun uncheckSubGoal(goalId: String, subGoalId: String, goalPoints: Long): Long

    suspend fun modifySubGoal(
        goalId: String,
        subGoalId: String,
        name: String,
        description: String,
        date: String,
        time: String,
        context: Context
    )

    suspend fun deleteSubGoal(
        goalId: String,
        goalPoints: Long,
        subGoalId: String,
        finished: Boolean,
        context: Context
    )
}
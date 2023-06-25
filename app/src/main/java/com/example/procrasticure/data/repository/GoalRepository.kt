package com.example.procrasticure.data.repository

import android.content.Context
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.viewModels.BigViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

interface GoalRepository {
    val database: FirebaseFirestore

    suspend fun getGoals(sessionViewModel: BigViewModel, goalArrayList: ArrayList<Goal>): ArrayList<Goal>

    suspend fun listenToChange(sessionViewModel: BigViewModel, goalArrayList: ArrayList<Goal>): ArrayList<Goal>

    fun getGoalsById(goalId: String, goalArrayList: ArrayList<Goal>): Goal?

    suspend fun finishGoal(goalId: String, goalPoints: Long, sessionViewModel: BigViewModel)

    suspend fun addGoal(goal: Goal, context: Context)

    suspend fun sortByCriteria(sessionViewModel: BigViewModel, goalArrayList: ArrayList<Goal>, criteria: String, order: Query.Direction): ArrayList<Goal>

    suspend fun deleteGoal(goalId: String, context: Context)
}
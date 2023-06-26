package com.example.procrasticure.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.GoalRepositoryImpl
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class GoalsViewModel @Inject constructor(
    private val sessionViewModel: BigViewModel,
    private val goalRepositoryImpl: GoalRepositoryImpl
) : ViewModel() {
    private var _goals = MutableStateFlow(ArrayList<Goal>())
    val goals: ArrayList<Goal> = ArrayList()

    val goalsState: StateFlow<ArrayList<Goal>> = _goals.asStateFlow()


    init {
        Log.d(TAG, "init is started")
        viewModelScope.launch {
            getGoals(sessionViewModel)
            listenToChanges(sessionViewModel)
        }
    }

    suspend fun getGoals(sessionViewModel: BigViewModel) {
        _goals.value =
            goalRepositoryImpl.getGoals(sessionViewModel = sessionViewModel, goalArrayList = goals)
    }

    private suspend fun listenToChanges(sessionViewModel: BigViewModel) {
        _goals.value = goalRepositoryImpl.listenToChange(
            sessionViewModel = sessionViewModel,
            goalArrayList = goals
        )
    }

    suspend fun finishGoal(goalId: String, goalPoints: Long, sessionViewModel: BigViewModel) {
        goalRepositoryImpl.finishGoal(
            goalId = goalId,
            sessionViewModel = sessionViewModel,
            goalPoints = goalPoints
        )
    }

    suspend fun addGoal(goal: Goal, context: Context) {
        goalRepositoryImpl.addGoal(goal = goal, context = context)
    }

    suspend fun sortGoalsByName(sessionViewModel: BigViewModel) {
        _goals.value = goalRepositoryImpl.sortByCriteria(
            sessionViewModel = sessionViewModel,
            goalArrayList = goals,
            criteria = "name",
            order = Query.Direction.ASCENDING
        )
    }

    suspend fun sortGoalsByDeadlineAscending(sessionViewModel: BigViewModel) {
        _goals.value = goalRepositoryImpl.sortByCriteria(
            sessionViewModel = sessionViewModel,
            goalArrayList = goals,
            criteria = "date",
            order = Query.Direction.ASCENDING
        )
    }

    suspend fun sortGoalsByDeadlineDescending(sessionViewModel: BigViewModel) {
        _goals.value = goalRepositoryImpl.sortByCriteria(
            sessionViewModel = sessionViewModel,
            goalArrayList = goals,
            criteria = "date",
            order = Query.Direction.DESCENDING
        )
    }

    suspend fun deleteGoal(goalId: String, context: Context) {
        goalRepositoryImpl.deleteGoal(goalId = goalId, context)
    }

    suspend fun modifyGoal(
        goalId: String,
        name: String,
        description: String,
        date: String,
        time: String,
        context: Context
    ) {
        goalRepositoryImpl.modifyGoal(goalId, name, description, date, time, context)
    }
}
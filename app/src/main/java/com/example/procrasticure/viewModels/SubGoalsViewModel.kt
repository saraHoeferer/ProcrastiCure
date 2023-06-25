package com.example.procrasticure.viewModels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.SubGoalRepositoryImpl
import com.google.android.gms.common.GooglePlayServicesUtilLight
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubGoalsViewModel@Inject constructor(id: String, private val subGoalsRepository: SubGoalRepositoryImpl) : ViewModel() {
    private val id = id
    private var _subGoals : MutableLiveData<ArrayList<Goal>> = MutableLiveData<ArrayList<Goal>>()

    val subGoals: ArrayList<Goal> = ArrayList()

    init {
        Log.d(ContentValues.TAG, "init is started")
        viewModelScope.launch {
            getSubGoals()
            listenToChanges()
        }
    }
    private suspend fun getSubGoals(){
        _subGoals.value = subGoalsRepository.getSubGoals(id, subGoals)
    }
    private suspend fun listenToChanges(){
        _subGoals.value = subGoalsRepository.listenToChanges(id, subGoals)
    }

    suspend fun addSubGoal(goalId: String, subGoal: Goal, context: Context){
        subGoalsRepository.addSubGoal(goalId = goalId, subGoal = subGoal, context = context)
    }

    suspend fun checkSubGoal(goalId: String, subGoalId: String, goalPoints: Long): Long{
       return subGoalsRepository.checkSubGoal(goalId = goalId, subGoalId = subGoalId, goalPoints = goalPoints)
    }

    suspend fun uncheckSubGoal(goalId: String, subGoalId: String, goalPoints: Long): Long{
        return subGoalsRepository.uncheckSubGoal(goalId = goalId, subGoalId = subGoalId, goalPoints = goalPoints)
    }



}